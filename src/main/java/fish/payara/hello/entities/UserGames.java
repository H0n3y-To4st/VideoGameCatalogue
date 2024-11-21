package fish.payara.hello.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "user_games")
@NamedQueries({
        @NamedQuery(name = UserGames.QUERY_BY_USER_ID, query = "SELECT ug FROM UserGames ug WHERE ug.user.id = :userId"),
        @NamedQuery(name = "UserGames.findByUserIdAndGameId", query = "SELECT ug FROM UserGames ug WHERE ug.user.id = :userId AND ug.game = :gameId"),
        @NamedQuery(name = "UserGames.findStateByUserIdAndGameId", query = "SELECT ug.gameStatus FROM UserGames ug WHERE ug.user.id = :userId AND ug.game = :gameId")
})
public class UserGames {

   public static final String QUERY_BY_USER_ID = "UserGames.findByUserId";

    public enum gamestate {
        BACKLOG,
        PLAYING,
        DROPPED,
        COMPLETED,
        WISHLISTED,
        FAVOURITED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic
    @Column(name = "user_games_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserAccount user;

    @Column(name = "game_id", nullable = false)
    private Integer game;

    @Enumerated(EnumType.STRING)
    @Column(name = "game_state", nullable = false)
    private gamestate gameStatus;

    public UserGames() {
    }

    public UserGames(UserAccount user, Integer game) {
        this.user = user;
        this.game = game;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public Integer getGame() {
        return game;
    }

    public void setGame(Integer game) {
        this.game = game;
    }

    public UserAccount getUser() {
        return user;
    }

    public gamestate getGameState() {
        return gameStatus;
    }

    public void setGameState(gamestate gameStatus) {
        this.gameStatus = gameStatus;
    }

}
