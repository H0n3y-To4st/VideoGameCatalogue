package fish.payara.hello.service;

import fish.payara.hello.entities.UserAccount;
import jakarta.ejb.Stateless;
import jakarta.ejb.LocalBean;
import jakarta.faces.context.FacesContext;
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

        public UserAccount getUser(String username, String password){
            return em.createNamedQuery("UserAccount.findByUsernameAndPassword", UserAccount.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getSingleResult();
        }

        public boolean checkLoggedIn(){
            try {
                FacesContext facesContext = FacesContext.getCurrentInstance();
                UserAccount user = (UserAccount) facesContext.getExternalContext().getSessionMap().get("user");
                if (user == null) {
//                    facesContext.getExternalContext().redirect("login.xhtml");
                    return false;
                }
//                facesContext.getExternalContext().redirect("dashboard.xhtml");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
}