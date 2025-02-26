package fish.payara.hello.jsf;


import fish.payara.hello.service.RegisterService;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;

import java.io.Serializable;

@Named(value = "registerBean")
@RequestScoped
public class RegisterBean implements Serializable {

    private String username;
    private String email;
    private String password;
    private String message;

    @Inject
    private RegisterService registerService;

    @Inject
    private NavigationController navigationController;

    public String registerAccount() {
        if (checkRegisterValid()) {
            try {
                registerService.registerUser(username, email, password);
                navigationController.index();
            } catch (Exception e) {
                throw new RuntimeException("Error registering user", e);
            }
        }
        return null;
    }

    public boolean checkRegisterValid() {
        if (registerService.usernameTaken(username)) {
            message = "Username already taken";
            return false;
        }
        if (registerService.emailTaken(email)) {
            message = "Email already taken";
            return false;
        }
        return true;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }
}