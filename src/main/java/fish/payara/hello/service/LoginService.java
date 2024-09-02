package fish.payara.hello.service;

import fish.payara.hello.entities.UserAccount;
import jakarta.ejb.Stateless;
import jakarta.ejb.LocalBean;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.io.Serializable;

@Stateless
@LocalBean
public class LoginService implements Serializable {

        @PersistenceContext
        private EntityManager em;

        public boolean checkForAccount(String username, String password){
            try {
                UserAccount user = em.createNamedQuery("UserAccount.findByUsername", UserAccount.class)
                        .setParameter("username", username)
                        .getSingleResult();
                return user.getPassword().equals(password);
            } catch (Exception e) {
                return false;
            }
        }
}