package fish.payara.hello.service;
import fish.payara.hello.entities.UserAccount;
import jakarta.ejb.Stateless;
import jakarta.ejb.LocalBean;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
@LocalBean
public class UserAccountService {

    @PersistenceContext
    private EntityManager em;

    public Integer getUserId(String username){
       return em.createNamedQuery("UserAccount.findIDByUsername", Integer.class)
                .setParameter("username", username).getSingleResult();
    }

    public UserAccount getUser(Integer id){
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
