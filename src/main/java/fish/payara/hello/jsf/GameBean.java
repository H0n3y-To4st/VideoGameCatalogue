package fish.payara.hello.jsf;


import fish.payara.hello.entities.Games;
import fish.payara.hello.restapi.IGDBService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named(value = "gameBean")
@RequestScoped
public class GameBean implements Serializable {

    @Inject
    private IGDBService igdbService;

    private List<Games> games;
    private String searchQuery;
    private List<Games> searchGames;
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

    public Games getSelectedGame() {
        return selectedGame;
    }

    public void setSelectedGame(Games selectedGame) throws IOException {
        if (selectedGame != null) {
            this.selectedGame = selectedGame;
            Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
            flash.put("selectedGame", this.selectedGame);
            FacesContext.getCurrentInstance().getExternalContext().redirect("game.xhtml?gameId=" + this.selectedGame.getId());
        }
    }

    public List<Games> searchGamesByName(String name) {
        return igdbService.searchGamesByName(name, false);
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
}
