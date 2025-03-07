package fish.payara.hello.jsf;


import fish.payara.hello.entities.Games;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import java.io.Serializable;

@Named(value = "navBean")
@ViewScoped
public class NavigationController implements Serializable {

    private Games selectedGame;

    // Login system
    public String login() {
        return "/login/login.xhtml?faces-redirect=true";
    }

    public String register() {
        return "register.xhtml?faces-redirect=true";
    }

    public String index() {
        return "/games/index.xhtml?faces-redirect=true";
    }

    public String dashboard() {
        return "/games/dashboard.xhtml?faces-redirect=true";
    }

    //    Ajax does not have access to the game var so this is a workaround, detects the event and redirects to the page
    public void onGameSelect(SelectEvent<Games> event) {
        selectedGame = event.getObject();
    }

    public String gameDetails() {
        if (selectedGame != null) {
            return "/games/game.xhtml?gameId=" + selectedGame.getId() + "&faces-redirect=true";
        }
        return null;
    }

}