package fish.payara.hello.restapi.dto;

import java.util.List;

public class UserIDs {

    private List<UserID> userIds;

    public UserIDs() {
    }

    public UserIDs(List<UserID> userIds) {
        this.userIds = userIds;
    }

    public List<UserID> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<UserID> userIds) {
        this.userIds = userIds;
    }
}
