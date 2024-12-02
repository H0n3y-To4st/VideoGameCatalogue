package fish.payara.hello;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.util.Objects;

@Embeddable
public class UserGameStatesId {
    @Column(name = "user_games_id", nullable = false)
    private Integer userGamesId;

    @Enumerated(EnumType.STRING)
    @Column(name = "game_state", nullable = false)
    private GameState gameState;

    // Getters, Setters, hashCode, equals
    public Integer getUserGamesId() {
        return userGamesId;
    }

    public void setUserGamesId(Integer userGamesId) {
        this.userGamesId = userGamesId;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserGameStatesId that = (UserGameStatesId) o;
        return Objects.equals(userGamesId, that.userGamesId) &&
                gameState == that.gameState;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userGamesId, gameState);
    }
}
