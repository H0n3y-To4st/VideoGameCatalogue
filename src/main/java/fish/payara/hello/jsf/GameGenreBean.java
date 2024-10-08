package fish.payara.hello.jsf;
import fish.payara.hello.entities.Genres;
import fish.payara.hello.service.GameService;
import jakarta.ejb.EJB;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import java.util.List;

@Named(value = "gameGenreBean")
@RequestScoped
public class GameGenreBean {

    @EJB
    GameService service;

    public GameGenreBean() {
    }

    public List<Genres> getGameGenre(int gameId){
        return service.getGameGenre(gameId);
    }
}
