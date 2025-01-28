package fish.payara.hello.jsf;

import fish.payara.hello.entities.Games;
import fish.payara.hello.entities.UserAccount;
import fish.payara.hello.service.LoginService;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named(value = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    @Inject
    private LoginService loginService;

    private String username;
    private String password;
    private String message;

    Logger logger = Logger.getLogger(LoginBean.class.getName());

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMessage() {
        return message;
    }

    public String checkForAccount() {
        if (loginService.checkForAccount(username, password)) {
            try {
                UserAccount user = loginService.getUser(username, password);
                FacesContext facesContext = FacesContext.getCurrentInstance();
                facesContext.getExternalContext().getSessionMap().put("user", user);
                facesContext.getExternalContext().redirect("index.xhtml");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        } else {
            message = "Invalid username or password";
            logger.log(Level.SEVERE, "Login failed");
            return "login";
        }
    }

    public boolean checkLoggedIn() {
        return loginService.checkLoggedIn();
    }

    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            request.login(username, password);
            context.getExternalContext().redirect("/games/index.xhtml");
        } catch (ServletException se) {
            logger.log(Level.SEVERE, se.getMessage(), se);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void logout() {
        try {
            ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).logout();
        } catch (ServletException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    }
}
