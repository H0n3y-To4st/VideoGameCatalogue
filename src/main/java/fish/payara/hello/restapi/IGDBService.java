package fish.payara.hello.restapi;

import fish.payara.hello.entities.Games;
import fish.payara.hello.restapi.client.IGDBClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;

@ApplicationScoped
public class IGDBService {

    @Inject
    @RestClient
    private IGDBClient igdbClient;

    public List<Games> getTopGames() {
        String body = """
                fields name,genres.name,rating,category, cover.url;
                where rating >= 90 & category = 0 & themes != (42); sort rating_count desc;
                limit 24;
                """;
        return igdbClient.getTopGames(body);
    }

    public List<Games> getGameByID(int gameId) {
        String body = "fields id, name, genres.name, rating, cover.url; where id = " + gameId + ";";
        return igdbClient.getGameByID(body);
    }

    public List<Games> searchGamesByName(String gameName, boolean advancedSearch) {
        String body = "fields name,genres.name,rating, cover.url;\n" +
                "where name ~ *\"" + gameName + "\"* & category = 0 & themes != (42); limit 3;";
        return igdbClient.searchGamesByName(body);
    }

    public Games getSelectedGameDetails(int gameId) {
        String body = """
                fields name,involved_companies.company.name,genres.name,
                first_release_date,rating,summary,cover.url,screenshots.url;
                """ + "where id = " + gameId + ";";
        return igdbClient.getSelectedGameDetails(body);
    }
}
