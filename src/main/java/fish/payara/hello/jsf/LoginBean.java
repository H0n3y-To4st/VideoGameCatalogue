package fish.payara.hello.jsf;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.servlet.http.HttpSession;

@Named(value = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {

//    these attributes are for my outdated "MyLogin" page, not the current one
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

    public boolean checkLoggedIn() {
        return username != null;
    }

    public String login() {
        if ("user".equals(username) && "password".equals(password)) {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            session.setAttribute("username", username);
            message = "Welcome, " + username + "!";
            return "/games/index.xhtml?faces-redirect=true";
        } else {
            message = "Invalid login. Please try again.";
            return "/login/login.xhtml?faces-redirect=true";
        }
    }

    public String logout() {
        try {
            ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).logout();
        } catch (ServletException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/games/index.xhtml?faces-redirect=true";
    }
}
