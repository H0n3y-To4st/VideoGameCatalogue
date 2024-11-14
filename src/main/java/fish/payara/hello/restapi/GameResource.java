package fish.payara.hello.restapi;

import fish.payara.hello.entities.Games;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/games")
public class GameResource {

    @Inject
    IGDBService igdbService;

//    @GET
//    @Path("/{gameName}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getGameByName(@PathParam("gameName") String gameName) {
//        List<Games> games = igdbService.searchGamesByName(gameName, false);
//        if (games.isEmpty()) {
//            return Response.status(Response.Status.NOT_FOUND).build();
//        }
//        return Response.ok(games).build();
//    }

    @GET
    @Path("/{gameId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGameByID(@PathParam("gameId") int gameId) {
        Games games = igdbService.getSelectedGameDetails(gameId);
        if (games.getId() == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(games).build();
    }

    @GET
    @Path("/top")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Games> getTopGames() {
        List<Games> games = igdbService.getTopGames();
        if (games.isEmpty()) {
//            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return games;
    }

    //include more functionality here, complete the jax-rs endpoints, refer to endpoint link
    // can i include update delete and create here?
    // finish the client and root resources and then will be done with jaxrs

}
