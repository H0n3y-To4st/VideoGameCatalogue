/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package fish.payara.hello.jsf;

import fish.payara.hello.entities.Games;
import fish.payara.hello.entities.UserAccount;
import fish.payara.hello.service.GameService;
import fish.payara.hello.service.IGDBService;
import fish.payara.hello.service.UserGamesService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 * @author IsmahHussain
 */
@Named(value = "gameBean")
@ViewScoped
public class GameBean implements Serializable {

    @Inject
    private GameService gameService;

    @Inject
    private IGDBService igdbService;

    private List<Games> games;

    public GameBean() {
        //for JPA
    }

    @PostConstruct
    public void init() {
        games = igdbService.getTopGames();
    }

    public List<Games> getGames() {
        return games;
    }

    public void setGames(List<Games> games) {
        this.games = games;
    }
}
