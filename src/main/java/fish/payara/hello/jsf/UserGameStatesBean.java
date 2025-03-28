package fish.payara.hello.jsf;


import fish.payara.hello.GameState;
import fish.payara.hello.UserGameStatesId;
import fish.payara.hello.restapi.dto.UpdateGameStates;
import fish.payara.hello.service.UserGameStatesService;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Named(value = "userGameStatesBean")
@SessionScoped
public class UserGameStatesBean implements Serializable {

    private List<GameState> gameStates;
    private List<GameState> selectedGameStates;
    private GameState selectedGameState;
    private int selectedGameId;

    @Inject
    private UserGamesBean userGamesBean;

    @Inject
    private UserGameStatesService userGameStatesService;

    @Inject
    private UserAccountBean userAccountBean;

    Logger logger = Logger.getLogger(UserGameStatesBean.class.getName());

    @PostConstruct
    public void init() {
        gameStates = List.of(GameState.values());
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

    public GameState getSelectedGameState() {
        return selectedGameState;
    }

    public void setSelectedGameState(GameState selectedGameState) {
        this.selectedGameState = selectedGameState;
    }

    public int getSelectedGameId() {
        return selectedGameId;
    }

    public void setSelectedGameId(int selectedGameId) {
        logger.info("Selected game id: " + selectedGameId);
        this.selectedGameId = selectedGameId;
    }

    public List<GameState> getGameStatesByUserGameId(int gameId) {
        return userGameStatesService.getGameStatesByUserGameId(userGamesBean.getUserGameId(gameId));
    }

    public List<UserGameStatesId> getUserGameIdsBySelectedGameState(GameState selectedGameState) {
        return userGameStatesService.getUserGameIdsBySelectedGameState(selectedGameState);
    }

    public void updateGameStates(int gameId, List<GameState> selectedGameStates) {
        UpdateGameStates request = new UpdateGameStates();
        request.setUserId(userAccountBean.getUserID());
        request.setSelectedGameStates(selectedGameStates);

        userGameStatesService.updateGameStates(request.getUserId(), gameId, request.getSelectedGameStates());

        // This is just for demo for demonstrating accessing the REST API from the JSF managed bean
//        try (Client client = ClientBuilder.newClient()) {
//            Response response = client.target("http://localhost:8080/videogame-catalogue-3.9.8/app/games/update/" + gameId + "/dashboard")
//                    .request(MediaType.APPLICATION_JSON)
//                    .put(Entity.entity(request, MediaType.APPLICATION_JSON));
//
//            if (response.getStatus() == 200) {
//                logger.log(Level.INFO, "Updated game state");
//                PrimeFaces.current().ajax().update("gameTable");
//            } else {
//                logger.log(Level.SEVERE, "Failed to update game state");
//            }
//        } catch (Exception e) {
//            logger.log(Level.SEVERE, "An error occurred while updating game states", e);
//        }
    }

    // add method to highlight already saved game states, and update the UI to reflect the changes
    // change this bean to a view scoped bean for better performance?
}
