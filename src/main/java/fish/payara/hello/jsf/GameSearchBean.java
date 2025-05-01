package fish.payara.hello.jsf;

import fish.payara.hello.entities.Games;
import fish.payara.hello.restapi.IGDBService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
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

    private List<String> genres;
    private List<String> platforms;
    private List<String> gameModes;

    @PostConstruct
    public void init() {
        searchResults = igdbService.searchGamesByName("", true);
            genres = new ArrayList<>(List.of(
                    "Point-and-click", "Fighting", "Shooter", "Music", "Puzzle", "Racing",
                    "Real Time Strategy (RTS)", "Role-playing (RPG)", "Simulator", "Sport", "Strategy",
                    "Turn-based strategy (TBS)", "Tactical", "Hack and slash/Beat 'em up", "Quiz/Trivia", "Pinball",
                    "Adventure", "Indie", "Arcade", "Visual Novel", "Card & Board Game", "MOBA"
            ));

            platforms = new ArrayList<>(List.of(
                    "Xbox", "PlayStation 5", "PlayStation 4", "PlayStation Vita", "Xbox 360",
                    "Nintendo Switch", "Nintendo DS", "iOS", "PlayStation 3", "Xbox 360",
                    "Wii U", "Wii", "Nintendo DS", "Xbox", "PlayStation",
                    "Linux", "PC (Microsoft Windows)", "DOS"
            ));

            gameModes = new ArrayList<>(List.of(
                    "Single player", "Multiplayer", "Co-operative", "Split screen",
                    "Massively Multiplayer Online (MMO)", "Battle Royale"
            ));
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

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<String> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<String> platforms) {
        this.platforms = platforms;
    }

    public List<String> getGameModes() {
        return gameModes;
    }

    public void setGameModes(List<String> gameModes) {
        this.gameModes = gameModes;
    }
}
