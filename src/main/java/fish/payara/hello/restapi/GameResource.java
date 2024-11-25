package fish.payara.hello.restapi;

import fish.payara.hello.entities.Games;
import fish.payara.hello.entities.UserAccount;
import fish.payara.hello.entities.UserGames;
import fish.payara.hello.service.UserAccountService;
import fish.payara.hello.service.UserGamesService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/games")
public class GameResource {

    @Inject
    IGDBService igdbService;

    @Inject
    UserGamesService userGamesService;

    @Inject
    UserAccountService userAccountService;

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
        return games;
    }
    /*
    Instead, you manage user-specific metadata in your database, such as which game belongs to which collection
    (e.g., "wishlist," "completed").
    This allows you to create or update the collection assignments without modifying the core game details.
     */
    @PUT
    @Path("/update/{gameId}/{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateGameDetails(@PathParam("gameId") int gameId, @PathParam("userId") int userId) {
//        userGamesService.updateGameSaveState(userId, gameId, gameStates);
        return Response.ok().build();
    }

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
    public Response saveGameToDashboard(@PathParam("gameId") int gameId, @PathParam("userId") int userId) {
//        if (user == null) {
//            return Response.status(Response.Status.NOT_FOUND).build();
//        }
        List<UserGames.gamestate> gamestate = List.of(UserGames.gamestate.BACKLOG);
        userGamesService.saveGameToDashboard(userId, gameId, gamestate);
        return Response.ok().build();
    }
}
