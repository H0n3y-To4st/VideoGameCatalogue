package fish.payara.hello.restapi;

import fish.payara.hello.entities.Games;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Collections;
import java.util.List;

@ApplicationScoped
public class IGDBService {

//    private String BASE_URI = "https://api.igdb.com/v4";

    //TODO: set these as environment variables
    private String CLIENT_ID = "13itn7518vwktd1u8ifuc3oaik3z1s";
    private String ACCESS_TOKEN = "Bearer r4x03x3eqqnkprmxmiihpulz96z2gc";

    //TODO: microprofile rest client
//    @Inject
//    @RestClient
//    private IGDBClient igdbClient;

    @POST
    public List<Games> getTopGames() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("https://api.igdb.com/v4/games");

        String body = "fields name,genres.name,rating,category, cover.url;\n" +
                "where rating >= 90 & category = 0 & themes != (42); sort rating_count desc;\n" +
                "limit 24;";
        Response response = target.request(MediaType.APPLICATION_JSON)
                .header("Client-ID", CLIENT_ID)
                .header("Authorization", ACCESS_TOKEN)
                .post(Entity.json(body));

        List<Games> games = Collections.emptyList();
        if (response.getStatus() == 200) {
            games = response.readEntity(new GenericType<List<Games>>() {});
        } else {
            System.out.println("Error: " + response.getStatus());
        }
        response.close();
        client.close();

        return games;
    }

    @POST
    public List<Games> getGameByID(int gameId) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("https://api.igdb.com/v4/games");

        String body = "fields id, name, genres.name,aggregated_rating; where id = " + gameId + ";";
        Response response = target.request(MediaType.APPLICATION_JSON)
                .header("Client-ID", CLIENT_ID)
                .header("Authorization", ACCESS_TOKEN)
                .post(Entity.json(body));

        List<Games> games = response.readEntity(new GenericType<List<Games>>() {});
        response.close();
        client.close();
        return games;
    }

    @POST
//    @Path("/search")
    public List<Games> searchGamesByName(String gameName, boolean advancedSearch) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("https://api.igdb.com/v4/games");

        String body = "fields name,genres.name,rating, cover.url;\n"+
                "where themes != (42);\n" +
                "where name ~ *\"" + gameName + "\"*; limit 3;";
//        if (!advancedSearch) {
//            body += "limit 3;";
//        }
        Response response = target.request(MediaType.APPLICATION_JSON)
                .header("Client-ID", CLIENT_ID)
                .header("Authorization", ACCESS_TOKEN)
                .post(Entity.json(body));

        List<Games> games = response.readEntity(new GenericType<List<Games>>() {});
        response.close();
        client.close();
        return games;
    }

    @POST
    public Games getSelectedGameDetails(int gameId) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("https://api.igdb.com/v4/games");

        String body = "fields name,genres.name,rating, cover.url;\n"
                + "where id = " + gameId + ";";
        Response response = target.request(MediaType.APPLICATION_JSON)
                .header("Client-ID", CLIENT_ID)
                .header("Authorization", ACCESS_TOKEN)
                .post(Entity.json(body));

        Games game = response.readEntity(new GenericType<Games>() {});
        response.close();
        client.close();
        return game;
    }
}