package fish.payara.hello.jsf;

import fish.payara.hello.entities.UserAccount;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

import java.io.Serializable;

@Named(value = "userAccountBean")
@SessionScoped
public class UserAccountBean implements Serializable {

    public Integer getLoggedInUserId() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UserAccount user = (UserAccount) facesContext.getExternalContext().getSessionMap().get("user");
        return user != null ? user.getId() : null;
    }

    public boolean isLoggedIn() {
        return getLoggedInUserId() != null;
    }
}