package fish.payara.hello.jsf;


import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;

@Named(value = "navBean")
@ViewScoped
public class NavigationController implements Serializable {

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

    public String advancedSearch() {
        return "/games/advancedSearch.xhtml?faces-redirect=true";
    }

}