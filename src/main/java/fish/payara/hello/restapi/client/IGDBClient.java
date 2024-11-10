package fish.payara.hello.restapi.client;
import fish.payara.hello.entities.Games;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import java.util.List;

//@RegisterRestClient(baseUri = "https://api.igdb.com/v4")
public interface IGDBClient {

//    @POST
//    @Path("/games")
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    List<Games> getTopGames(@HeaderParam("Client-ID") String clientId,
//                            @HeaderParam("Authorization") String accessToken);
}