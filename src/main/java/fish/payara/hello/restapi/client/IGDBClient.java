package fish.payara.hello.restapi.client;

import fish.payara.hello.entities.Games;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@RegisterRestClient(baseUri = "https://api.igdb.com/v4/games")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ClientHeaderParam(name = "Client-ID", value = "{clientId}")
@ClientHeaderParam(name = "Authorization", value = "{accessToken}")
public interface IGDBClient {

    default String clientId() {
        return ConfigProvider.getConfig().getValue("CLIENT_ID", String.class);
    }

    default String accessToken() {
        return ConfigProvider.getConfig().getValue("ACCESS_TOKEN", String.class);
    }

    @POST
    List<Games> getTopGames(String body);

    @POST
    List<Games> getGameByID(String body);

    @POST
    List<Games> searchGamesByName(String body);

    @POST
    Games getSelectedGameDetails(String body);
}
