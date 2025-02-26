package fish.payara.hello.restapi;


import fish.payara.hello.entities.Games;
import fish.payara.hello.restapi.dto.UpdateGameStates;
import fish.payara.hello.restapi.dto.UserID;
import fish.payara.hello.service.UserGameStatesService;
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
    UserGameStatesService userGameStatesService;

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
        return igdbService.getTopGames();
    }

    @PUT
    @Path("/update/{gameId}/dashboard")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateSavedGameStates(@PathParam("gameId") int gameId, UpdateGameStates updateGameStates) {
        userGameStatesService.updateGameStates(updateGameStates.getUserId(), gameId, updateGameStates.getSelectedGameStates());
        return Response.ok().build();
    }

    @DELETE
    @Path("/delete/{gameId}/dashboard")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteGameFromDashboard(@PathParam("gameId") int gameId, @QueryParam("userId") int userId) {
        userGamesService.removeGameFromDashboard(userId, gameId);
        return Response.noContent().build();
    }

    @POST
    @Path("/save/{gameId}/dashboard")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveGameToDashboard(@PathParam("gameId") int gameId, UserID userId) {
        if (userId == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        userGamesService.saveGameToDashboard(userId.getId(), gameId);
        return Response.ok().build();
    }
}
