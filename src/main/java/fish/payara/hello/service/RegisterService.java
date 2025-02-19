package fish.payara.hello.service;

import fish.payara.hello.entities.UserAccount;
import jakarta.ejb.Stateless;
import jakarta.ejb.LocalBean;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

@Stateless
@LocalBean
public class RegisterService {

    @PersistenceContext
    private EntityManager em;

    public boolean usernameTaken(String username) {
        List<UserAccount> users = em.createNamedQuery("UserAccount.findByUsername", UserAccount.class)
                .setParameter("username", username)
                .getResultList();
        return !users.isEmpty();
    }

    public boolean emailTaken(String email) {
        List<UserAccount> users = em.createNamedQuery("UserAccount.findByEmail", UserAccount.class)
                .setParameter("email", email)
                .getResultList();
        return !users.isEmpty();
    }

    /*
    need to use entity manager instead of jpa because we want to insert data into the database
    and jpa is for: SELECT, UPDATE AND DELETE
     */

    public void registerUser(String username, String email, String password) {
        try {
            UserAccount user = new UserAccount(username, email, encodeSHA256(password));
            em.persist(user);
        } catch (Exception e) {
            throw new RuntimeException("Error registering user within registerservice", e);
        }
    }

    public static String encodeSHA256(String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return encodeSHA256(password, "");
    }

    public static String encodeSHA256(String password, String salt)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes(StandardCharsets.UTF_8));
        md.update(salt.getBytes(StandardCharsets.UTF_8));
        byte[] digest = md.digest();
        return Base64.getEncoder().encodeToString(digest);
    }
}