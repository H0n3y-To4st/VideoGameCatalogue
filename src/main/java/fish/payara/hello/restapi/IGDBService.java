package fish.payara.hello.restapi;

import fish.payara.hello.entities.Games;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.ws.rs.*;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.StringReader;
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

        //TODO: specify for games only, not stuff like dlcs
        String body = "fields name,genres.name,aggregated_rating,category; where aggregated_rating > 90; limit 40;";
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

        for (Games game : games) {
            game.setCoverUrl(getGameCoverByName(game.getFullName()));
        }

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
    public String getGameCoverByName(String gameName) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("https://api.igdb.com/v4/covers");

        String body = "fields url; where game.name = \"" + gameName + "\";";        Response response = target.request(MediaType.APPLICATION_JSON)
                .header("Client-ID", CLIENT_ID)
                .header("Authorization", ACCESS_TOKEN)
                .post(Entity.json(body));

        String url = "No cover available";
        if (response.getStatus() == 200) {
            String jsonResponse = response.readEntity(String.class);
            JsonReader jsonReader = Json.createReader(new StringReader(jsonResponse));
            JsonArray jsonArray = jsonReader.readArray();
            jsonReader.close();

            if (!jsonArray.isEmpty()) {
                JsonObject jsonObject = jsonArray.getJsonObject(0);
                url = jsonObject.getString("url", "No cover available").replace("thumb", "cover_big");
                if (!url.equals("No cover available")) {
                    url = "https:" + url;
                }
            }
        } else {
            System.out.println("Error: " + response.getStatus());
        }

        response.close();
        client.close();
        return url;
    }
}