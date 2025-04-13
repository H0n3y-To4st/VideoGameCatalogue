package fish.payara.hello.jsf;

import fish.payara.hello.entities.Games;
import fish.payara.hello.restapi.IGDBService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named("gameSearchBean")
@ViewScoped
public class GameSearchBean implements Serializable {

    @Inject
    private IGDBService igdbService;

    private String searchQuery;
    private List<Games> searchResults;

    @PostConstruct
    public void init() {
        searchResults = igdbService.searchGamesByName("", true);
    }

    public List<Games> searchGamesByName(String name) {
        searchResults = igdbService.searchGamesByName(name, false);
        return searchResults;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public List<Games> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<Games> searchResults) {
        this.searchResults = searchResults;
    }

    public void storeQuery() {
        // TODO: implement logic to store the search query
    }
}
