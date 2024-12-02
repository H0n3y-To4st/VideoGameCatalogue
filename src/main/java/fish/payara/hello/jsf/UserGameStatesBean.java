package fish.payara.hello.jsf;

import fish.payara.hello.GameState;
import fish.payara.hello.service.UserGameStatesService;
import fish.payara.hello.service.UserGamesService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

@Named(value = "userGameStatesBean")
@ViewScoped
public class UserGameStatesBean implements Serializable {

    private List<GameState> gameStates;
    private List<GameState> selectedGameStates;

    private int selectedGameId;

    @Inject
    private UserGameStatesService userGameStatesService;

    Logger logger = Logger.getLogger(UserGameStatesBean.class.getName());

    @Inject
    private UserGamesBean userGamesBean;

    @PostConstruct
    public void init() {
        gameStates = List.of(GameState.values());
        selectedGameStates = new ArrayList<>(userGameStatesService.getSelectedGameStates());
    }

    public void saveGameAndStates(int gameId, List<GameState> selectedGameStates) {
        userGamesBean.saveGameToDashboard(gameId);
        userGameStatesService.saveGameStates(userGamesBean.getUserGameId(gameId), selectedGameStates);
        selectedGameStates = new ArrayList<>(userGameStatesService.getSelectedGameStates());
    }

    public List<GameState> getGameStates() {
        return gameStates;
    }

    public void setGameStates(List<GameState> gameStates) {
        this.gameStates = gameStates;
    }

    public List<GameState> getSelectedGameStates() {
        return selectedGameStates;
    }

    public void setSelectedGameStates(List<GameState> selectedGameStates) {
        this.selectedGameStates = selectedGameStates;
    }

    public int getSelectedGameId() {
        return selectedGameId;
    }

    public void setSelectedGameId(int selectedGameId) {
        logger.info("Selected game id: " + selectedGameId);
        this.selectedGameId = selectedGameId;
    }

    public List<GameState> getGameStatesByUserGameId(int gameId) {
        List<GameState> gameStates = userGameStatesService.getGameStatesByUserGameId(userGamesBean.getUserGameId(gameId));
        gameStates.sort(Comparator.comparingInt(Enum::ordinal));
        return gameStates;
    }
}