/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package fish.payara.hello.service;

import fish.payara.hello.entities.Games;
import fish.payara.hello.entities.UserAccount;
import fish.payara.hello.entities.UserGames;
import jakarta.ejb.Stateless;
import jakarta.ejb.LocalBean;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author IsmahHussain
 */
@Stateless
@LocalBean
public class GameService {

    @PersistenceContext
    EntityManager em;

    //TODO: stop user from saving the same game to their dashboard, handle this
    /*
    stateless = public = transations
     */
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
            Logger.getLogger(GameService.class.getName()).log(Level.INFO, "No existing game found for user, proceeding to save new game", e);
        }
        if (checkForExisting == null) {
            UserGames userGames = new UserGames(user, game.getId());
            em.persist(userGames);
        }
    }
//        // add primeface notification pop up if saved already within else block, mb change ui for save game button to show it already saved with primefaces
//        Logger.getLogger(GameService.class.getName()).log(Level.INFO, "Game already saved to the dashboard");

    public void removeGameFromDashboard(UserAccount user, Games game) {
//        UserGames userGames = em.createNamedQuery("UserGames.findByUserIdAndGameId", UserGames.class)
//                .setParameter("userId", user.getId())
//                .setParameter("gameId", game.getId())
//                .getSingleResult();
//        em.remove(userGames);
    }
}
