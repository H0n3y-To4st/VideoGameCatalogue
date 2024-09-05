package fish.payara.hello.jsf;
import fish.payara.hello.entities.UserAccount;
import fish.payara.hello.service.LoginService;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;

import java.io.Serializable;

@Named(value = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    @Inject
    private LoginService loginService;

    private String username;
    private String password;
    private String message;

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

    /*
     this works because of how jsf handles it,
     don't need to specify in faces-config.xml
     */

    public String checkForAccount(){
        if (loginService.checkForAccount(username, password)) {
            try {
                UserAccount user = loginService.getUser(username, password);
                FacesContext facesContext = FacesContext.getCurrentInstance();
                facesContext.getExternalContext().getSessionMap().put("user", user);
                facesContext.getExternalContext().redirect("dashboard.xhtml");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        } else {
            message = "Invalid username or password";
            return "login";
        }
    }

    public void checkLoggedIn(){
        loginService.checkLoggedIn();
    }
}
