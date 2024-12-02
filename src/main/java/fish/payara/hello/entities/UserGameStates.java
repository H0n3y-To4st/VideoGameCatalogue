package fish.payara.hello.entities;

import fish.payara.hello.GameState;
import fish.payara.hello.UserGameStatesId;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "user_game_states")
public class UserGameStates {

    public enum gamestate {
        BACKLOG,
        PLAYING,
        DROPPED,
        COMPLETED,
        WISHLISTED,
        FAVOURITED
    }

    @EmbeddedId
    private UserGameStatesId id;

    @MapsId("userGamesId") // Maps the userGamesId part of the composite key
    @ManyToOne
    @JoinColumn(name = "user_games_id", nullable = false)
    private UserGames userGames;

    public UserGameStates() {
    }

    public UserGameStates(UserGameStatesId id, UserGames  userGames) {
        this.id = id;
        this.userGames = userGames;
    }

    public UserGameStatesId getId() {
        return id;
    }

    public void setId(UserGameStatesId id) {
        this.id = id;
    }

    public UserGames getUserGames() {
        return userGames;
    }

    public void setUserGames(UserGames userGames) {
        this.userGames = userGames;
    }
}