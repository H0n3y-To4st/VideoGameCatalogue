package fish.payara.hello.service;
import fish.payara.hello.entities.UserAccount;
import jakarta.ejb.Stateless;
import jakarta.ejb.LocalBean;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
@LocalBean
public class UserAccountService implements Serializable {

    @PersistenceContext
    private EntityManager em;

    Logger logger = Logger.getLogger(UserAccountService.class.getName());

    public UserAccount getUserByUsername(String username){
        try {

            UserAccount user = em.createNamedQuery("UserAccount.findIDByUsername", UserAccount.class)
                    .setParameter("username", username).getSingleResult();
            logger.log(Level.SEVERE, "Getting user: " + username + "user ID" + user.getId());
            return user;
        } catch (Exception e){
            return null;
        }
    }

    public UserAccount getUserByID(Integer id){
        return em.createNamedQuery("UserAccount.findById", UserAccount.class)
                .setParameter("id", id).getSingleResult();
    }

    public UserAccount getUserByUsernameAndPassword(String username, String password){
        return em.createNamedQuery("UserAccount.findByUsernameAndPassword", UserAccount.class)
                .setParameter("username", username)
                .setParameter("password", password)
                .getSingleResult();
    }
}
