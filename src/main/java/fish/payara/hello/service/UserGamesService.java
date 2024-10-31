package fish.payara.hello.service;

import fish.payara.hello.entities.Games;
import fish.payara.hello.entities.UserAccount;
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
import java.util.logging.Level;
import java.util.logging.Logger;

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

    // Updated to return all games the user has saved
    public List<Games> listAllGamesInDashboard(int userId) {
        List<UserGames> userGamesList = em.createNamedQuery(UserGames.QUERY_BY_USER_ID, UserGames.class)
                .setParameter("userId", userId)
                .getResultList();
        List<Games> games = new ArrayList<>();
        for (UserGames userGame : userGamesList) {
            // Fetch each game by its ID and add it to the list
            games.addAll(igdbService.getGameByID(userGame.getGame()));
        }
        return games;
    }

    public void saveGameToDashboard(UserAccount user, Games game) {
        if (game == null) {
            return;
        }
        UserGames checkForExisting = null;
        try {
            checkForExisting = em.createNamedQuery("UserGames.findByUserIdAndGameId", UserGames.class)
                    .setParameter("userId", user.getId())
                    .setParameter("gameId", game.getId())
                    .getSingleResult();
        } catch (NoResultException e) {
            Logger.getLogger(UserGamesService.class.getName()).log(Level.INFO, "Game not saved to the dashboard");
        }
        if (checkForExisting == null) {
            UserGames userGames = new UserGames(user, game.getId());
            em.persist(userGames);
        }
    }
//        // add primeface notification pop up if saved already within else block, mb change ui for save game button to show it already saved with primefaces
//        Logger.getLogger(GameService.class.getName()).log(Level.INFO, "Game already saved to the dashboard");

    public void removeGameFromDashboard(UserAccount user, Games game) {
        try {
            UserGames userGames = em.createNamedQuery("UserGames.findByUserIdAndGameId", UserGames.class)
                    .setParameter("userId", user.getId())
                    .setParameter("gameId", game.getId())
                    .getSingleResult();
            em.remove(userGames);
        } catch (NoResultException e) {
            System.out.println("No game found for user: " + user.getId() + " and game: " + game.getId());

        }
    }
}