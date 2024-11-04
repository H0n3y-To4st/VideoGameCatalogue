/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package fish.payara.hello.jsf;

import fish.payara.hello.entities.Games;
import fish.payara.hello.restapi.IGDBService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

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

    public GameBean() {
        //for JPA
    }

    @PostConstruct
    public void init() {
        games = igdbService.getTopGames();
//        try {
//            String requestBody = new String(FacesContext.getCurrentInstance().getExternalContext().getRequest().getInputStream().readAllBytes());
//            JsonObject jsonObject = Json.createReader(new StringReader(requestBody)).readObject();
//            int gameId = jsonObject.getInt("id");
//            selectedGame = igdbService.getGameByID(gameId).get(0);
//        } catch (IOException | JsonException e) {
//            // Handle the exception, e.g., log an error or set a default value
//            System.err.println("Error reading request body: " + e.getMessage());
//        }
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
            this.selectedGame = igdbService.getSelectedGameDetails(selectedGame.getId());
            if (this.selectedGame != null) {
                FacesContext facesContext = FacesContext.getCurrentInstance();
                facesContext.getExternalContext().redirect("game.xhtml");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
