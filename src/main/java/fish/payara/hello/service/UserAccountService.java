package fish.payara.hello.service;
import fish.payara.hello.entities.UserAccount;
import jakarta.ejb.Stateless;
import jakarta.ejb.LocalBean;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.io.Serializable;

@Stateless
@LocalBean
public class UserAccountService implements Serializable {

    @PersistenceContext
    private EntityManager em;


    public UserAccount getUserByUsername(String username){
        try {
            return em.createNamedQuery("UserAccount.findByUsername", UserAccount.class)
                    .setParameter("username", username).getSingleResult();
        } catch (Exception e){
            return null;
        }
    }

    public UserAccount getUserByID(Integer id){
        return em.createNamedQuery("UserAccount.findById", UserAccount.class)
                .setParameter("id", id).getSingleResult();
    }

    public int getUserIdByUsername(String username){
        return em.createNamedQuery("UserAccount.findIDByUsername", Integer.class)
                .setParameter("username", username).getSingleResult();
    }
}
