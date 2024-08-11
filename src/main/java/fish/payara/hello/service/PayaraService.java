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
//import jakarta.persistence.TypedQuery;

import java.util.List;

/**
 *
 * @author IsmahHussain
 */
@Stateless
@LocalBean
public class PayaraService {
    
    @PersistenceContext
    private EntityManager em;

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

/*
  input game id to get the genre
  search thru the genres table to get the genre of the game
    return the genre of the game
 */
    public List<Genres> getGameGenre(int gameId){
        return em.createNamedQuery(Genres.QUERY_BY_GAME_ID, Genres.class)
                .setParameter("GameId", gameId)
                .getResultList();
    }
}
