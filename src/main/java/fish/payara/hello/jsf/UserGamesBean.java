package fish.payara.hello.jsf;

import fish.payara.hello.GameState;
import fish.payara.hello.entities.Games;
import fish.payara.hello.restapi.dto.UserID;
import fish.payara.hello.service.UserGameStatesService;
import fish.payara.hello.service.UserGamesService;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.primefaces.PrimeFaces;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named(value = "userGamesBean")
@SessionScoped
public class UserGamesBean implements Serializable {

    private List<Games> games;
    private String username;
    private List<GameState> selectedGameStates;

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
        username = userAccountBean.getUsername();
        logger.info("UserGamesBean instantiated");
    }

    public void saveGameAndStates(int gameId) {
        int userID = userAccountBean.getUserID().getId();

        //save game to dashboard, include body params
        Client client = ClientBuilder.newClient();
        Response response = client.target("http://localhost:8080/videogame-catalogue-3.9.8/app/games/save/" + gameId + "/dashboard")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(userID, MediaType.APPLICATION_JSON));

        if (response.getStatus() == 200) {
            logger.log(Level.INFO, "Saved game to dashboard");
            selectedGameStates = new ArrayList<>(userGameStatesBean.getSelectedGameStates());
            userGameStatesService.saveGameStates(getUserGameId(gameId), selectedGameStates);
        } else {
            logger.log(Level.SEVERE, "Failed to saved game to dashboard");
        }
        response.close();
        client.close();
    }

    public void removeGameFromDashboard(int gameId) {
        int userID = userAccountBean.getUserID().getId();

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/videogame-catalogue-3.9.8/app/games/delete/" + gameId + "/dashboard")
                .queryParam("userId", userID);
        Response response = target.request(MediaType.APPLICATION_JSON).delete();

        if (response.getStatus() == 204) {
            games = userGamesService.listAllGamesInDashboard(userID);
            PrimeFaces.current().ajax().update("gameTable");
        }  else {
            logger.log(Level.SEVERE, "Failed to delete game from dashboard");
        }
    }

    public List<Games> getGames() {
        games = userGamesService.listAllGamesInDashboard(userAccountBean.getUserID().getId());
        return games;
    }

    public void setGames(List<Games> games) {
        this.games = games;
    }

    public int getUserGameId(int gameId) {
        return userGamesService.getUserGameId(userAccountBean.getUserID().getId(), gameId);
    }
}
