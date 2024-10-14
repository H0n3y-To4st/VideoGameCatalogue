package fish.payara.hello.jsf;
import fish.payara.hello.entities.Genres;
import fish.payara.hello.service.GameService;
import jakarta.ejb.EJB;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import java.util.List;

/**
 *
 * @author IsmahHussain
 */
@Named(value = "genreBean")
@RequestScoped
public class GenreBean {

    @EJB
    private GameService service;

    public GenreBean() {
    }

    public List<Genres> listAllGenres(){
//        return service.listAllGenres();
        return null;
    }
}
