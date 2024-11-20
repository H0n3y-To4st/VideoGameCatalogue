/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package fish.payara.hello.jsf;

import fish.payara.hello.entities.Games;
import fish.payara.hello.restapi.IGDBService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author IsmahHussain
 */
@Named(value = "gameBean")
@ViewScoped
public class GameBean implements Serializable {

    @Inject
    private IGDBService igdbService;

    private List<Games> games;

    private String searchQuery;
    private List<Games> searchGames;

    private Games selectedGame;

    Logger logger = Logger.getLogger(getClass().getName());

    public GameBean() {
        //for JPA
    }

    @PostConstruct
    public void init() {
        games = fetchTopGames();

        //this makes it available when the game page is loaded (but not refreshed) since it is retrieved from the flash scope
        //when the game page is refreshed, the selectedGame is null because it is no longer in the flash scope
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap();
        String gameIdParam = params.get("gameId");

        if (gameIdParam != null) {
            int gameId = Integer.parseInt(gameIdParam);
            selectedGame = igdbService.getSelectedGameDetails(gameId);
        } else {
            selectedGame = (Games) facesContext.getExternalContext().getFlash().get("selectedGame");
        }
        if (selectedGame != null) {
            logger.log(Level.INFO, "Selected Game: " + selectedGame.getName());
        }
    }

    public List<Games> getGames() {
        return games;
    }

    public void setGames(List<Games> games) {
        this.games = games;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public List<Games> getSearchGames() {
        return searchGames;
    }

    public void setSearchGames(List<Games> searchGames) {
        this.searchGames = searchGames;
    }

    public List<Games> searchGamesByName(String name) {
        return igdbService.searchGamesByName(name, false);
    }

    public Games getSelectedGame() {
        return selectedGame;
    }

    public void setSelectedGame(Games selectedGame) {
        try {
//            this.selectedGame = igdbService.getSelectedGameDetails(selectedGame.getId());
            this.selectedGame = fetchGameDetails(selectedGame.getId());
            //this is how you pass data between pages during a redirect
            if (this.selectedGame != null) {
                FacesContext facesContext = FacesContext.getCurrentInstance();
                facesContext.getExternalContext().getFlash().put("selectedGame", this.selectedGame);
                facesContext.getExternalContext().redirect("game.xhtml?gameId=" + this.selectedGame.getId());
            } else {
                logger.log(Level.SEVERE, "Selected Game is null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Games> fetchTopGames() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/videogame-catalogue-3.9.8/app/games/top");
        Response response = target.request().get();

        if (response.getStatus() == 200) {
            games = response.readEntity(new GenericType<List<Games>>() {
            });
            logger.log(Level.INFO, "Fetched top games");
        } else {
            logger.log(Level.SEVERE, "Failed to fetch top games");
        }
        response.close();
        client.close();

        return games;
    }
    //this is using the endpoint from the REST API to set the selectedGame
    private Games fetchGameDetails(int gameId) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/videogame-catalogue-3.9.8/app/games/" + gameId);
        Response response = target.request().get();

        if (response.getStatus() == 200) {
            selectedGame = response.readEntity(Games.class);
            logger.log(Level.INFO, "Selected Game: " + selectedGame.getName());
        } else {
            logger.log(Level.SEVERE, "Failed to fetch game details");
        }
        response.close();
        client.close();
        return selectedGame;
    }
}
