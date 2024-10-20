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

}
