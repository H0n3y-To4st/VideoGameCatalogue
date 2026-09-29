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
                fields name,genres.name,rating,game_type, cover.url;
                where rating >= 90 & game_type = 0 & themes != (42); sort rating_count desc;
                limit 24;
                """;
        return igdbClient.getTopGames(body);
    }

    public List<Games> getGamesByID(int gameId) {
        String body = "fields id, name, genres.name, rating, cover.url; where id = " + gameId + ";";
        return igdbClient.getGamesByID(body);
    }

    public Games getGameByID(int gameId) {
        String body = "fields id, name, genres.name, rating, cover.url; where id = " + gameId + ";";
        return igdbClient.getGameByID(body);
    }

    public List<Games> searchGamesByName(String gameName, boolean advancedSearch) {
        String body = "fields name,genres.name,rating, cover.url;\n" +
                "where name ~ *\"" + gameName + "\"* & game_type = 0 & themes != (42);";
        if (!advancedSearch) {
            body += "limit 3;";
        } else {
//            setting limit for performance
            body += "limit 100;";
        }
        return igdbClient.searchGamesByName(body);
    }

    public List<Games> searchGamesByFilters(String gameName, List<String> selectedGenres, List<String> selectedPlatforms, List<String> selectedGameModes) {
        StringBuilder body = new StringBuilder("fields name,genres.name,rating,cover.url;\n");
        body.append("where name ~ *\"").append(gameName).append("\"* & game_type = 0 & themes != (42)");

        if (selectedGenres != null && !selectedGenres.isEmpty()) {
            body.append(" & genres.name = (");
            body.append(String.join(",", selectedGenres.stream().map(genre -> "\"" + genre + "\"").toList()));
            body.append(")");
        }

        if (selectedPlatforms != null && !selectedPlatforms.isEmpty()) {
            body.append(" & platforms.name = (");
            body.append(String.join(",", selectedPlatforms.stream().map(platform -> "\"" + platform + "\"").toList()));
            body.append(")");
        }

        if (selectedGameModes != null && !selectedGameModes.isEmpty()) {
            body.append(" & game_modes.name = (");
            body.append(String.join(",", selectedGameModes.stream().map(mode -> "\"" + mode + "\"").toList()));
            body.append(")");
        }

        body.append("; limit 100;");

        return igdbClient.searchGamesByName(body.toString());
    }

    public Games getSelectedGameDetails(int gameId) {
        String body = """
                fields name,involved_companies.company.name,genres.name,
                first_release_date,rating,summary,cover.url,screenshots.url;
                """ + "where id = " + gameId + ";";
        return igdbClient.getSelectedGameDetails(body);
    }

    public List<Games> getTopGamesByGenre(String genreName) {
        String body = """
        fields name, cover.url;
        where rating >= 90 & game_type = 0 & themes != (42) & genres.name = (" """ + genreName +
        """
        "); sort rating_count desc; limit 5;
        """;
        return igdbClient.getTopGamesByGenre(body);
    }

}
