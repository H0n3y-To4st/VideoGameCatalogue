package fish.payara.hello.service;

import fish.payara.hello.entities.Games;
import fish.payara.hello.entities.Genres;
import fish.payara.hello.entities.UserAccount;
import fish.payara.hello.entities.UserGames;
import jakarta.ejb.Stateless;
import jakarta.ejb.LocalBean;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}