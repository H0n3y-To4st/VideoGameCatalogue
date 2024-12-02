package fish.payara.hello.jsf;

import fish.payara.hello.GameState;
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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named(value = "userGamesBean")
@ViewScoped
public class UserGamesBean implements Serializable {

    private List<Games> games;

    private int userId;

    private List<GameState> gameStates;
    private List<GameState> selectedGameStates;

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
        gameStates = List.of(GameState.values());
        selectedGameStates = new ArrayList<>();
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
        userGamesService.saveGameToDashboard(userId, gameId);
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

    public int getUserGameId(int gameId) {
        return userGamesService.getUserGameId(userId, gameId);
    }
}
