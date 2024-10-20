package fish.payara.hello.jsf;

import fish.payara.hello.entities.Games;
import fish.payara.hello.entities.UserGames;
import fish.payara.hello.service.UserGamesService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.*;

@Named(value = "userGamesBean")
@ViewScoped
public class UserGamesBean implements Serializable {

    private int gameId;
    private int userId;

    private List<Games> games;

    @Inject
    private UserGamesService service;

    @Inject
    private LoginBean loginBean;

    @Inject
    private UserAccountBean userAccountBean;

    public UserGamesBean() {
        //for JPA
    }

    @PostConstruct
    public void init() {
        userId = userAccountBean.getLoggedInUserId(loginBean.getUsername());
        games = service.listAllGamesInDashboard(userId);
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<Games> getGames() {
        return games;
    }

    public void setGames(List<Games> games) {
        this.games = games;
    }
}
