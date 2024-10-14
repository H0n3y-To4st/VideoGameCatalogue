package fish.payara.hello.service;

import fish.payara.hello.entities.Games;
import fish.payara.hello.entities.Genres;
import fish.payara.hello.entities.UserAccount;
import fish.payara.hello.entities.UserGames;
import jakarta.ejb.Stateless;
import jakarta.ejb.LocalBean;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

/**
 *
 * @author IsmahHussain
 */
@Stateless
@LocalBean
public class UserGamesService {

    @PersistenceContext
    private EntityManager em;

//    public List<UserGames> listAllGamesInDashboard(int userId){
////        return em.createNamedQuery(UserGames.QUERY_BY_USER_ID, UserGames.class)
////                .setParameter("userId", userId)
////                .getResultList();
//    }
}
