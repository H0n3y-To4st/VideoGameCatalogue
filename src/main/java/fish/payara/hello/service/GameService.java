/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package fish.payara.hello.service;

import fish.payara.hello.entities.Games;
import fish.payara.hello.entities.Genres;
import fish.payara.hello.entities.UserAccount;
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
public class GameService {
    
    @PersistenceContext
    private EntityManager em;

//    TODO: this currently just returns the first user in the database, so their name is displayed on the home page
    public String getMessage(){
        return em.createNamedQuery("UserAccount.findAll", UserAccount.class)
                .setMaxResults(1)
                .getResultStream()
                .findFirst()
                .get()
                .getUsername();
    }
    
    public List<Games> listAllGames(){
        return em.createNamedQuery(Games.QUERY_ALL, Games.class)
                .getResultList();
    }

    public List<Genres> listAllGenres(){
        return em.createNamedQuery(Genres.QUERY_ALL, Genres.class)
                .getResultList();
    }

    public List<Genres> getGameGenre(int gameId){
        return em.createNamedQuery(Genres.QUERY_BY_GAME_ID, Genres.class)
                .setParameter("GameId", gameId)
                .getResultList();
    }
}
