package fish.payara.hello.jsf;


import fish.payara.hello.entities.Games;
import fish.payara.hello.restapi.IGDBService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named(value = "gameBean")
@ViewScoped
public class GameBean implements Serializable {

    @Inject
    private IGDBService igdbService;

    private List<Games> games;
    private Games selectedGame;

    private static final Logger logger = Logger.getLogger(GameBean.class.getName());

    public GameBean() {
    }

    @PostConstruct
    public void init() {
        games = fetchTopGames();
    }

    public void loadGameDetails() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap();
        String gameIdParam = params.get("gameId");

        if (gameIdParam != null) {
            try {
                int gameId = Integer.parseInt(gameIdParam);
                selectedGame = igdbService.getSelectedGameDetails(gameId);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Invalid game ID: {0}", gameIdParam);
            }
        } else {
            Flash flash = facesContext.getExternalContext().getFlash();
            selectedGame = (Games) flash.get("selectedGame");
        }

        if (selectedGame != null) {
            logger.log(Level.INFO, "Loaded selected game: {0}", selectedGame.getName());
        }
    }

    public List<Games> getGames() {
        return games;
    }

    public void setGames(List<Games> games) {
        this.games = games;
    }

    public Games getSelectedGame() {
        return selectedGame;
    }

    public void setSelectedGame(Games selectedGame) {
        this.selectedGame = selectedGame;
    }

    //    this is specifically for triggering via Ajax
    public void onGameSelect(SelectEvent<Games> event) {
        selectedGame = event.getObject();
        if (selectedGame != null) {
            try {
                String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
                FacesContext.getCurrentInstance().getExternalContext()
                        .redirect(contextPath + "/games/game.xhtml?gameId=" + selectedGame.getId());
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Redirection failed", e);
            }
        }
    }

    //    this is specifically for triggering via command buttons
    public String redirectToGameDetails(int gameId) {
        return "/games/game.xhtml?faces-redirect=true&gameId=" + gameId;
    }

    public void onSearchGameSelect(SelectEvent<Games> event) {
        selectedGame = event.getObject();
        if (selectedGame != null) {
            logger.log(Level.INFO, "Selected game: {0}", selectedGame.getName());
        }
    }

    public Games getGameById(int id) {
        return igdbService.getGameByID(id);
    }

    private List<Games> fetchTopGames() {
        try {
            games = igdbService.getTopGames();
            logger.log(Level.INFO, "Fetched top games.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to fetch top games.", e);
        }
        return games;
    }

    public List<Games> getTopGamesByGenre(String genreName) {
        return igdbService.getTopGamesByGenre(genreName);
    }
}
