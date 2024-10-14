package fish.payara.hello.entities;
//
//import jakarta.persistence.*;
//
//@Entity
//@Table(name = "user_games")
//@NamedQueries({
//        @NamedQuery(name = UserGames.QUERY_BY_USER_ID, query = "SELECT ug.game FROM UserGames ug WHERE ug.user.id = :userId"),
//        @NamedQuery(name = "UserGames.findByUserIdAndGameId", query = "SELECT ug FROM UserGames ug WHERE ug.user.id = :userId AND ug.game.id = :gameId")
//})
public class UserGames {
////
//    public static final String QUERY_BY_USER_ID = "UserGames.findByUserId";
////
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Basic
//    @Column(name = "user_games_id")
//    private Long id;
////
////    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "user_id", nullable = false)
//    private UserAccount user;
////
////    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "game_id", nullable = false)
//    private Games game;
//
//    public UserGames() {
//    }
//
//    public UserGames(UserAccount user, Games game) {
//        this.user = user;
//        this.game = game;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public Games getGame() {
//        return game;
//    }
//
//    public void setGame(Games game) {
//        this.game = game;
//    }
//
//    public UserAccount getUser() {
//        return user;
//    }
//
}
