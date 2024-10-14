/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package fish.payara.hello.jsf;

import fish.payara.hello.entities.Games;
import fish.payara.hello.entities.Genres;
import fish.payara.hello.entities.UserAccount;
import fish.payara.hello.service.GameService;
import fish.payara.hello.service.IGDBService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

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
    private GameService service;

    @Inject
    private IGDBService igdbService;

    private List<Games> games;

//    private List<Genres> genres;

    public GameBean() {
        //for JPA
    }

    @PostConstruct
    public void init() {
        games = igdbService.getTopGames("name,genres.name,aggregated_rating", 36);
//        genres = igdbService.getGameGenres(games);
    }

    public void saveGameToDashboard(Games game) throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UserAccount user = (UserAccount) facesContext.getExternalContext().getSessionMap().get("user");
        if (user == null) {
            facesContext.getExternalContext().redirect("login.xhtml");
            return;
        }
        service.saveGameToDashboard(user, game);
    }

    public void removeGameFromDashboard(Games game) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UserAccount user = (UserAccount) facesContext.getExternalContext().getSessionMap().get("user");
        service.removeGameFromDashboard(user, game);
//        userGamesBean.listAllGamesInDashboard(user.getId());
        facesContext.addMessage(null, new FacesMessage(game.getName() + " removed from dashboard"));
//        PrimeFaces.current().ajax().update("dashboard");
//        PrimeFaces.current().ajax().update("gameTable");
    }

    public List<Games> getGames() {
        return games;
    }

    public void setGames(List<Games> games) {
        this.games = games;
    }
}
