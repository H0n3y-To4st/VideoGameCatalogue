/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package fish.payara.hello.jsf;

import fish.payara.hello.entities.Games;
import fish.payara.hello.service.PayaraService;
import jakarta.ejb.EJB;
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

    @EJB
    private PayaraService service;

    public GameBean() {
    }
    
    public String getMessage(){
        return service.getMessage();
    }
    
    public List<Games> listAllGames(){
        return service.listAllGames();
    }
}
