/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package fish.payara.hello.jsf;

import fish.payara.hello.entities.Games;
import fish.payara.hello.entities.UserAccount;
import fish.payara.hello.service.GameService;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import java.util.List;

/**
 *
 * @author IsmahHussain
 */
@Named(value = "gameBean")
@RequestScoped
public class GameBean {

    @Inject
    private GameService service;

    public GameBean() {
    }
    
    public List<Games> listAllGames(){
        return service.listAllGames();
    }

    public void saveGameToDashboard(Games game) {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            UserAccount user = (UserAccount) facesContext.getExternalContext().getSessionMap().get("user");
            if (user == null) {
                facesContext.getExternalContext().redirect("login.xhtml");
                return;
            }
            service.saveGameToDashboard(user, game);
        } catch (Exception e) { //this exception is hidden, it just silently catches it
            e.printStackTrace();
        }
    }

    public void removeGameFromDashboard(Games game) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UserAccount user = (UserAccount) facesContext.getExternalContext().getSessionMap().get("user");
        service.removeGameFromDashboard(user, game);
    }
}
