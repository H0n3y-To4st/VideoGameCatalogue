package fish.payara.hello.jsf;

import fish.payara.hello.service.RegisterService;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;

@Named(value = "userGamesBean")
@RequestScoped
public class UserGamesBean {

    private int gameId;
    private int userId;

    @Inject
    private RegisterService registerService;

    public void saveGameToDashboard(){
//        registerService.saveGameToDashboard(gameId, userId);
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
