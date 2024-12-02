package fish.payara.hello.service;

import fish.payara.hello.GameState;
import fish.payara.hello.UserGameStatesId;
import fish.payara.hello.entities.UserGameStates;
import fish.payara.hello.entities.UserGames;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.List;

@Stateless
public class UserGameStatesService {

    @PersistenceContext
    private EntityManager em;

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

    public List<GameState> getGameStatesByUserGameId(int userGameId) {
        Query query = em.createQuery("SELECT ugs.id.gameState FROM UserGameStates ugs WHERE ugs.id.userGamesId = :userGameId");
        query.setParameter("userGameId", userGameId);
        return query.getResultList();
    }
}
