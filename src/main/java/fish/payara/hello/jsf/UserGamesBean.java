package fish.payara.hello.jsf;

import fish.payara.hello.entities.Games;
import fish.payara.hello.entities.UserAccount;
import fish.payara.hello.entities.UserGames;
import fish.payara.hello.service.UserGamesService;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named(value = "userGamesBean")
@ViewScoped
public class UserGamesBean implements Serializable {

    private List<Games> games;

    private int userId;

    private List<UserGames.gamestate> gameStates;
    private List<UserGames.gamestate> selectedGameStates;

    @Inject
    private UserGamesService userGamesService;

    @Inject
    private LoginBean loginBean;

    @Inject
    private UserAccountBean userAccountBean;

    Logger logger = Logger.getLogger(UserGamesBean.class.getName());

    public UserGamesBean() {
        logger.info("UserGamesBean instantiated");
    }

    @PostConstruct
    public void init() {
        gameStates = List.of(UserGames.gamestate.values());
        if (loginBean.checkLoggedIn()) {
            try {
                userId = userAccountBean.getLoggedInUserId(loginBean.getUsername());
                games = userGamesService.listAllGamesInDashboard(userId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void saveGameToDashboard(int gameId) {
//        FacesContext facesContext = FacesContext.getCurrentInstance();
//        UserAccount user = (UserAccount) facesContext.getExternalContext().getSessionMap().get("user");
//        if (user == null) {
//            facesContext.getExternalContext().redirect("login.xhtml");
//            return;
//        }
//        Client client = ClientBuilder.newClient();
//        WebTarget target = client.target("http://localhost:8080/videogame-catalogue-3.9.8/app/games/save/" + gameId + "/" + user.getId());
//        Response response = target.request().get();
//        if (response.getStatus() == 200) {
//            logger.log(Level.INFO, "Saved game to dashboard");
//        } else {
//            logger.log(Level.SEVERE, "Failed to saved game to dashboard");
//        }
//        response.close();
//        client.close();

        // why is the gamestate not being saved and is instead null?
        logger.log(Level.SEVERE, "Before saving, the selected game states are: " + selectedGameStates);
        userGamesService.saveGameToDashboard(userId, gameId, selectedGameStates);
    }

    public void removeGameFromDashboard(int gameId) {
        UserAccount user = userAccountBean.getUser(userId);
        if (user != null) {
            userGamesService.removeGameFromDashboard(user, gameId);

            // Refresh the list after removal
            games = userGamesService.listAllGamesInDashboard(user.getId());

            // Optionally update UI components
            PrimeFaces.current().ajax().update("gameTable");
        }
    }

    public List<Games> getGames() {
        return games;
    }

    public void setGames(List<Games> games) {
        this.games = games;
    }

//    public UserGames.gamestate[] getGameStateByGameID(int gameId) {
//        gameStates = userGamesService.getGameState(userId, gameId);
//        return gameStates;
//    }

    public List<UserGames.gamestate> getGameStates() {
        return gameStates;
    }

    public void setGameStates(List<UserGames.gamestate> gameStates) {
        this.gameStates = gameStates;
    }

    public List<UserGames.gamestate> getSelectedGameStates() {
        return selectedGameStates;
    }

    //TODO: the selected game states are correctly assigned but are then reset to null before being saved
    // is something reinitializing the userGamesBean?

    public void setSelectedGameStates(List<UserGames.gamestate> selectedGameStates) {
        logger.log(Level.SEVERE, "method call and the selected game states are: " + selectedGameStates);
        this.selectedGameStates = selectedGameStates;
    }
}
