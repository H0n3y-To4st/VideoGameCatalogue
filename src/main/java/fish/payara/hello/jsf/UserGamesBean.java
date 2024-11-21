package fish.payara.hello.jsf;

import fish.payara.hello.entities.Games;
import fish.payara.hello.entities.UserAccount;
import fish.payara.hello.entities.UserGames;
import fish.payara.hello.service.UserGamesService;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@Named(value = "userGamesBean")
@ViewScoped
public class UserGamesBean implements Serializable {

    private List<Games> games;

    private int userId;

    private UserGames.gamestate gameStates;

    @Inject
    private UserGamesService userGamesService;

    @Inject
    private LoginBean loginBean;

    @Inject
    private UserAccountBean userAccountBean;

    @PostConstruct
    public void init() {
        userId = userAccountBean.getLoggedInUserId(loginBean.getUsername());
        games = userGamesService.listAllGamesInDashboard(userId);
//        gameStates = List.of(UserGames.gamestate.values());
    }

    //need to redirect to login page if user is not logged in
    public void saveGameToDashboard(int gameId) throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UserAccount user = (UserAccount) facesContext.getExternalContext().getSessionMap().get("user");
        if (user == null) {
            facesContext.getExternalContext().redirect("login.xhtml");
            return;
        }
        userGamesService.saveGameToDashboard(user, gameId);
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

    public UserGames.gamestate getGameState(int gameId) {
        gameStates = userGamesService.getGameState(userId, gameId);
        return gameStates;
    }

    public void setGameState(UserGames.gamestate gameStates) {
        this.gameStates = gameStates;
    }
}
