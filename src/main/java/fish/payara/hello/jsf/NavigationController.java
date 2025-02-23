package fish.payara.hello.jsf;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named(value = "navBean")
@RequestScoped
public class NavigationController {

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
}