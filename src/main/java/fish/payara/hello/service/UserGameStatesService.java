package fish.payara.hello.service;

import fish.payara.hello.GameState;
import fish.payara.hello.UserGameStatesId;
import fish.payara.hello.entities.UserGameStates;
import fish.payara.hello.entities.UserGames;
import fish.payara.hello.jsf.UserAccountBean;
import fish.payara.hello.jsf.UserGamesBean;
import fish.payara.hello.restapi.dto.UserID;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@Stateless
public class UserGameStatesService implements Serializable {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserGamesBean userGamesBean;

    @Inject
    private UserAccountBean userAccountBean;

    @Inject
    private UserGamesService userGamesService;

    Logger logger = Logger.getLogger(UserGameStatesService.class.getName());

    public List<GameState> getSelectedGameStates() {
        // Implement logic to retrieve selected game states
        return List.of();
    }

    public void saveGameStates(int userGamesId, List<GameState> selectedGameStates) {
        UserGames userGames = em.find(UserGames.class, userGamesId);
        if (userGames != null) {
            for (GameState gameState : selectedGameStates) {
                UserGameStatesId id = new UserGameStatesId();
                id.setUserGamesId(userGamesId);
                id.setGameState(gameState);

                UserGameStates userGameStates = new UserGameStates();
                userGameStates.setId(id);
                userGameStates.setUserGames(userGames);

                em.persist(userGameStates);
            }
        } else {
            throw new IllegalArgumentException("UserGames not found for id: " + userGamesId);
        }
    }

    public void removeGameStates(int userGamesId) {
        try {
            List<UserGameStates> userGameStatesList = em.createNamedQuery("UserGameStates.findByUserGameId", UserGameStates.class)
                    .setParameter("userGameId", userGamesId)
                    .getResultList();
            for (UserGameStates userGameStates : userGameStatesList) {
                em.remove(userGameStates);
            }
        } catch (NoResultException e) {
            System.out.println("No game states found for userGameId: " + userGamesId);
        }
    }

    public List<GameState> getGameStatesByUserGameId(int userGameId) {
        Query query = em.createQuery("SELECT ugs.id.gameState FROM UserGameStates ugs WHERE ugs.id.userGamesId = :userGameId");
        query.setParameter("userGameId", userGameId);
        return query.getResultList();
    }

    // TODO: optimise this

    //get current game states for a user game
    //get selected game states
    //compare the two

    //using sets to more efficiently compare the two lists
    //time this
    public void updateGameStates(UserID userId, int gameId, List<GameState> selectedGameStates) {
        int userGamesId = userGamesService.getUserGameId(userId.getId(), gameId);

        List<GameState> alreadySavedGameStates = getGameStatesByUserGameId(userGamesId);
        Set<GameState> alreadySavedGameStatesSet = new HashSet<>(alreadySavedGameStates);
        Set<GameState> selectedGameStatesSet = new HashSet<>(selectedGameStates);

        UserGames userGames = em.find(UserGames.class, userGamesId);

        if (userGames != null) {
            //remove already saved game states from selected game states
            Set<GameState> statesToAdd = new HashSet<>(selectedGameStatesSet);
            statesToAdd.removeAll(alreadySavedGameStatesSet);

            Set<GameState> statesToRemove = new HashSet<>(alreadySavedGameStatesSet);
            statesToRemove.removeAll(selectedGameStatesSet);

            if (!statesToAdd.isEmpty()) {
                for (GameState newState : statesToAdd) {
                    UserGameStatesId id = new UserGameStatesId();
                    id.setUserGamesId(userGamesId);
                    id.setGameState(newState);

                    UserGameStates userGameStates = new UserGameStates();
                    userGameStates.setId(id);
                    userGameStates.setUserGames(userGames);

                    em.merge(userGameStates);
                }
            }

            if (!statesToRemove.isEmpty()) {
                Query query = em.createQuery("DELETE FROM UserGameStates ugs WHERE ugs.id.userGamesId = :userGamesId AND ugs.id.gameState IN :gameStates");
                query.setParameter("userGamesId", userGamesId);
                query.setParameter("gameStates", statesToRemove);
                query.executeUpdate();
            }
        } else {
            throw new IllegalArgumentException("userId: " + userId + " not found during update game state");
        }
    }

}