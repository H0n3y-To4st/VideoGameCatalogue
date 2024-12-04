package fish.payara.hello.service;

import fish.payara.hello.GameState;
import fish.payara.hello.entities.Games;
import fish.payara.hello.entities.UserAccount;
import fish.payara.hello.entities.UserGameStates;
import fish.payara.hello.entities.UserGames;
import fish.payara.hello.restapi.IGDBService;
import jakarta.ejb.Stateless;
import jakarta.ejb.LocalBean;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author IsmahHussain
 */
@Stateless
@LocalBean
public class UserGamesService {

    @Inject
    IGDBService igdbService;

    @PersistenceContext
    private EntityManager em;

    @Inject
    UserGameStatesService userGameStatesService;

    public List<Games> listAllGamesInDashboard(int userId) {
        List<UserGames> userGamesList = em.createNamedQuery(UserGames.QUERY_BY_USER_ID, UserGames.class)
                .setParameter("userId", userId)
                .getResultList();
        List<Games> games = new ArrayList<>();
        for (UserGames userGame : userGamesList) {
            games.addAll(igdbService.getGameByID(userGame.getGame()));
        }
        return games;
    }

    public void saveGameAndStates(int userId, int gameId, List<GameState> selectedGameStates) {
        saveGameToDashboard(userId, gameId);
        int userGameId = getUserGameId(userId, gameId);
        userGameStatesService.saveGameStates(userGameId, selectedGameStates);
    }

    public void saveGameToDashboard(int userId, int gameId) {
        try {
            UserGames checkForExisting = em.createNamedQuery("UserGames.findByUserIdAndGameId", UserGames.class)
                    .setParameter("userId", userId)
                    .setParameter("gameId", gameId)
                    .getSingleResult();
            em.merge(checkForExisting);
        } catch (NoResultException e) {
            UserAccount user = em.find(UserAccount.class, userId);

            UserGames userGames = new UserGames();
            userGames.setUser(user);
            userGames.setGame(gameId);
            em.persist(userGames);
        }
    }
//        add primeface notification pop up if saved already within else block, mb change ui for save game button to show it already saved with primefaces
//        Logger.getLogger(GameService.class.getName()).log(Level.INFO, "Game already saved to the dashboard");

    public void removeGameFromDashboard(int userId, int gameId) {
        try {
            UserGames userGames = em.createNamedQuery("UserGames.findByUserIdAndGameId", UserGames.class)
                    .setParameter("userId", userId)
                    .setParameter("gameId", gameId)
                    .getSingleResult();

            userGameStatesService.removeGameStates(userGames.getId());

            em.remove(userGames);
        } catch (NoResultException e) {
            System.out.println("No game found for user: " + userId + " and game: " + gameId);
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
