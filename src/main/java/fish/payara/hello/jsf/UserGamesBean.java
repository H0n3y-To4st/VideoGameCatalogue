package fish.payara.hello.jsf;

import fish.payara.hello.entities.Games;
import fish.payara.hello.entities.UserAccount;
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
    }

    //need to redirect to login page if user is not logged in
    public void saveGameToDashboard(Games game) throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UserAccount user = (UserAccount) facesContext.getExternalContext().getSessionMap().get("user");
        if (user == null) {
            facesContext.getExternalContext().redirect("login.xhtml");
            return;
        }
        userGamesService.saveGameToDashboard(user, game);
    }

    public void removeGameFromDashboard(Games game) {
        UserAccount user = userAccountBean.getUser(userId);
        if (user != null && game != null) {
            userGamesService.removeGameFromDashboard(user, game);

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
}
