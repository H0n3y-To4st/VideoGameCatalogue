package fish.payara.hello.service;

import fish.payara.hello.entities.Games;
import fish.payara.hello.service.client.IGDBClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Path("/games")
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

        String body = "fields name,genres.name,aggregated_rating; where aggregated_rating > 80; limit 18;";
        Response response = target.request(MediaType.APPLICATION_JSON)
                .header("Client-ID", CLIENT_ID)
                .header("Authorization", ACCESS_TOKEN)
                .post(Entity.json(body));

        // Return the response
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
}