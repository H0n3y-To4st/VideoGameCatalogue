package fish.payara.hello.jsf;


import fish.payara.hello.GameState;
import fish.payara.hello.entities.Games;
import fish.payara.hello.service.UserGameStatesService;
import fish.payara.hello.service.UserGamesService;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Named(value = "userGamesBean")
@SessionScoped
public class UserGamesBean implements Serializable {

    private List<Games> games;
    private List<GameState> selectedGameStates;
    private int userID;

    @Inject
    private UserGamesService userGamesService;

    @Inject
    private UserGameStatesService userGameStatesService;

    @Inject
    private UserGameStatesBean userGameStatesBean;

    @Inject
    private UserAccountBean userAccountBean;

    Logger logger = Logger.getLogger(UserGamesBean.class.getName());

    public UserGamesBean() {
    }

    @PostConstruct
    public void init() {
        selectedGameStates = new ArrayList<>();
        userID = userAccountBean.getUserID().getId();
        logger.info("UserGamesBean instantiated with userID: " + userID);
    }

    public void saveGameAndStates(int gameId) {
        userGamesService.saveGameToDashboard(userID, gameId);
        selectedGameStates = new ArrayList<>(userGameStatesBean.getSelectedGameStates());
        userGameStatesService.saveGameStates(getUserGameId(gameId), selectedGameStates);
    }

    public void removeGameFromDashboard(int gameId) {
        userGamesService.removeGameFromDashboard(userID, gameId);
        games = userGamesService.listAllGamesInDashboard(userID);
    }

    public List<Games> getGames() {
        games = userGamesService.listAllGamesInDashboard(userID);
        return games;
    }

    public void setGames(List<Games> games) {
        this.games = games;
    }

    public int getUserGameId(int gameId) {
        return userGamesService.getUserGameId(userID, gameId);
    }
}