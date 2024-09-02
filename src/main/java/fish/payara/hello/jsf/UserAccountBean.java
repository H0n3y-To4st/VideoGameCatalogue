package fish.payara.hello.jsf;
import fish.payara.hello.service.UserAccountService;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Named(value = "userAccountBean")
@RequestScoped
public class UserAccountBean {

    @Inject
    private UserAccountService userAccountService;

    @PersistenceContext
    private EntityManager em;

    public Integer getLoggedInUserId(String username) {
        return userAccountService.getUserId(username);
    }
}
