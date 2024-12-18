package fish.payara.hello.jsf;

import fish.payara.hello.GameState;
import fish.payara.hello.restapi.dto.UpdateGameStates;
import fish.payara.hello.restapi.dto.UserID;
import fish.payara.hello.service.UserGameStatesService;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.primefaces.PrimeFaces;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named(value = "userGameStatesBean")
@SessionScoped
public class UserGameStatesBean implements Serializable {

    private List<GameState> gameStates;
    private List<GameState> selectedGameStates;
    private int selectedGameId;

    @Inject
    private UserGamesBean userGamesBean;

    @Inject
    private UserGameStatesService userGameStatesService;

    @Named("userAccountBean")
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

    public void updateGameStates(int gameId, List<GameState> selectedGameStates) {

        int id = userAccountBean.getLoggedInUserId();
        UserID userId = new UserID();
        userId.setId(id);

        UpdateGameStates request = new UpdateGameStates();
        request.setUserId(userId);
        request.setSelectedGameStates(selectedGameStates);

            //this is just for demo for demonstrating accessing the REST API from the JSF managed bean
        try  (Client client = ClientBuilder.newClient()) {

            Response response = client.target("http://localhost:8080/videogame-catalogue-3.9.8/app/games/update/" + gameId + "/dashboard")
                    .request(MediaType.APPLICATION_JSON)
                    .put(Entity.entity(request, MediaType.APPLICATION_JSON));

            if (response.getStatus() == 200) {
                logger.log(Level.INFO, "Updated game state");
                PrimeFaces.current().ajax().update("gameTable");
            } else {
                logger.log(Level.SEVERE, "Failed to update game state");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred while updating game states", e);
        }
    }

}