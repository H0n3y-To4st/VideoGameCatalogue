package fish.payara.hello.restapi.dto;

public class UserID {

    private Integer id;

    public UserID() {
    }

    public UserID(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
