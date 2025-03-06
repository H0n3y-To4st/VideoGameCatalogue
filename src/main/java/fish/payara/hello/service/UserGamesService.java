package fish.payara.hello.service;

import fish.payara.hello.entities.Games;
import fish.payara.hello.entities.UserAccount;
import fish.payara.hello.entities.UserGames;
import fish.payara.hello.restapi.IGDBService;

import jakarta.annotation.Resource;
import jakarta.ejb.Stateless;
import jakarta.ejb.LocalBean;
import jakarta.enterprise.concurrent.ManagedExecutorService;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
@LocalBean
public class UserGamesService implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(UserGamesService.class.getName());

    @PersistenceContext
    private EntityManager em;

    @Inject
    private IGDBService igdbService;

    @Inject
    private UserGameStatesService userGameStatesService;

    @Resource
    private ManagedExecutorService mes; //5 threads, can mess with its limit, it's the managed thread pool for async tasks

    public List<Games> listAllGamesInDashboard(int userId) {
        List<UserGames> userGamesList = em.createNamedQuery(UserGames.QUERY_BY_USER_ID, UserGames.class)
                .setParameter("userId", userId)
                .getResultList();

        List<Games> games = new ArrayList<>();
        List<Future<List<Games>>> futures = new ArrayList<>();

        for (UserGames userGame : userGamesList) {
            Future<List<Games>> future = mes.submit(() -> igdbService.getGameByID(userGame.getGame()));
            futures.add(future);
        }

        for (Future<List<Games>> future : futures) {
            try {
                games.addAll(future.get()); // Wait for each Future to complete
            } catch (InterruptedException | ExecutionException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Error fetching game details", e);
            }
        }

        return games;
    }

    public void saveGameToDashboard(int userId, int gameId) {
        try {
            UserGames existingUserGame = em.createNamedQuery("UserGames.findByUserIdAndGameId", UserGames.class)
                    .setParameter("userId", userId)
                    .setParameter("gameId", gameId)
                    .getSingleResult();
            LOGGER.log(Level.INFO, "Game already saved for user {0}", userId);
            em.merge(existingUserGame);
        } catch (NoResultException e) {
            UserAccount user = em.find(UserAccount.class, userId);
            if (user == null) {
                throw new IllegalArgumentException("User not found: " + userId);
            }

            UserGames newUserGame = new UserGames();
            newUserGame.setUser(user);
            newUserGame.setGame(gameId);
            em.persist(newUserGame);

            LOGGER.log(Level.INFO, "Game {0} saved for user {1}", new Object[]{gameId, userId});
        }
    }

    public void removeGameFromDashboard(int userId, int gameId) {
        try {
            UserGames userGames = em.createNamedQuery("UserGames.findByUserIdAndGameId", UserGames.class)
                    .setParameter("userId", userId)
                    .setParameter("gameId", gameId)
                    .getSingleResult();

            userGameStatesService.removeGameStates(userGames.getId());
            em.remove(userGames);

            LOGGER.log(Level.INFO, "Game {0} removed from user {1} dashboard", new Object[]{gameId, userId});
        } catch (NoResultException e) {
            LOGGER.log(Level.WARNING, "No game found for user {0} and game {1}", new Object[]{userId, gameId});
        }
    }

    public int getUserGameId(int userId, int gameId) {
        try {
            UserGames userGame = em.createNamedQuery("UserGames.findByUserIdAndGameId", UserGames.class)
                    .setParameter("userId", userId)
                    .setParameter("gameId", gameId)
                    .getSingleResult();
            return userGame.getId();
        } catch (NoResultException e) {
            throw new IllegalArgumentException("No UserGames found for userId: " + userId + " and gameId: " + gameId);
        }
    }
}
