package fish.payara.hello.restapi;

import fish.payara.hello.entities.Games;
import fish.payara.hello.entities.UserAccount;
import fish.payara.hello.entities.UserGames;
import fish.payara.hello.jsf.UserGamesBean;
import fish.payara.hello.service.UserAccountService;
import fish.payara.hello.service.UserGamesService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.IOException;
import java.util.List;

@Path("/games")
public class GameResource {

    @Inject
    IGDBService igdbService;

    @Inject
    UserGamesService userGamesService;

    @Inject
    UserGamesBean userGamesBean;

    @Inject
    UserAccountService userAccountService;

    //FETCH GAME DATA = COMPLETE
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

    //FETCH GAME DATA = COMPLETE
    @GET
    @Path("/top")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Games> getTopGames() {
        List<Games> games = igdbService.getTopGames();
        return games;
    }
    /*
    Instead, you manage user-specific metadata in your database, such as which game belongs to which collection
    (e.g., "wishlist," "completed").
    This allows you to create or update the collection assignments without modifying the core game details.
     */
    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateGameDetails(Games game) {
        boolean isUpdated = true;
        userGamesBean.updateGameDetails(game);
        if (isUpdated) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    // delete game from dashboard - COMPLETE
    @DELETE
    @Path("/delete/{gameId}/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteGameFromDashboard(@PathParam("gameId") int gameId, @PathParam("userId") int userId) {
        UserAccount user = userAccountService.getUser(userId);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        userGamesService.removeGameFromDashboard(user, gameId);
        return Response.noContent().build();
    }

    @POST
    @Path("/save/{gameId}/{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveGameToDashboard(@PathParam("gameId") int gameId, @PathParam("userId") int userId) throws IOException {
        UserAccount user = userAccountService.getUser(userId);
        userGamesService.saveGameToDashboard(user, gameId);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok().build();
    }
}
