package fish.payara.hello.jsf;

import fish.payara.hello.entities.UserGames;
import fish.payara.hello.service.RegisterService;
import fish.payara.hello.service.UserGamesService;
import jakarta.ejb.EJB;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;

import java.util.List;

@Named(value = "userGamesBean")
@RequestScoped
public class UserGamesBean {

    private int gameId;
    private int userId;

    @EJB
    UserGamesService service;

    @Inject
    private RegisterService registerService;

    public List<UserGames> listAllGamesInDashboard(int userId){
        return service.listAllGamesInDashboard(userId);
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
}
