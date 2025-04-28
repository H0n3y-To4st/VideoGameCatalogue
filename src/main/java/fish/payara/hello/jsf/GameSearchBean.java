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

    private List<String> selectedGenres;
    private List<String> selectedPlatforms;
    private List<String> selectedGameModes;

    @PostConstruct
    public void init() {
        searchResults = igdbService.searchGamesByName("", true);
    }

    public List<Games> searchGamesByName(String name) {
        searchResults = igdbService.searchGamesByFilters(name, selectedGenres, selectedPlatforms, selectedGameModes);
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

    public List<String> getSelectedGenres() {
        return selectedGenres;
    }

    public void setSelectedGenres(List<String> selectedGenres) {
        this.selectedGenres = selectedGenres;
    }

    public List<String> getSelectedPlatforms() {
        return selectedPlatforms;
    }

    public void setSelectedPlatforms(List<String> selectedPlatforms) {
        this.selectedPlatforms = selectedPlatforms;
    }

    public List<String> getSelectedGameModes() {
        return selectedGameModes;
    }

    public void setSelectedGameModes(List<String> selectedGameModes) {
        this.selectedGameModes = selectedGameModes;
    }
}
