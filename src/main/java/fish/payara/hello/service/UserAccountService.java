package fish.payara.hello.service;
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
}
