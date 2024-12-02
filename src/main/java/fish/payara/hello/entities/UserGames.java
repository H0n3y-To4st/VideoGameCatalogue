package fish.payara.hello.entities;

//import fish.payara.hello.GamestateArrayConverter;
import jakarta.persistence.*;
import fish.payara.hello.GameState;

import java.util.List;

@Entity
@Table(name = "user_games")
@NamedQueries({
        @NamedQuery(name = UserGames.QUERY_BY_USER_ID, query = "SELECT ug FROM UserGames ug WHERE ug.user.id = :userId"),
        @NamedQuery(name = "UserGames.findByUserIdAndGameId", query = "SELECT ug FROM UserGames ug WHERE ug.user.id = :userId AND ug.game = :gameId")
})
public class UserGames {

   public static final String QUERY_BY_USER_ID = "UserGames.findByUserId";

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

    public void setUser(UserAccount user) {
        this.user = user;
    }
}
