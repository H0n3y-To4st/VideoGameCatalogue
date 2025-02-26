package fish.payara.hello.jsf;

import fish.payara.hello.entities.UserAccount;
import fish.payara.hello.restapi.dto.UserID;
import fish.payara.hello.service.UserAccountService;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;

@Named(value = "userAccountBean")
@SessionScoped
public class UserAccountBean implements Serializable {

    @Inject
    UserAccountService userAccountService;

    private String username;
    private String password;
    private UserID userID;

    public UserAccount getUserByUsername(String username) {
        return userAccountService.getUserByUsername(username);
    }

    public int getUserIdByUsername(String username) {
        return userAccountService.getUserIdByUsername(username);
    }

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

    public UserID getUserID() {
        return userID;
    }

    public Boolean checkLoggedIn() {
        return username != null;
    }

    public String login() {
        int userId = getUserIdByUsername(username);
        userID = new UserID(userId);
        return "/games/index.xhtml?faces-redirect=true";
    }
}