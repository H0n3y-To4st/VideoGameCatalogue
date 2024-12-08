package fish.payara.hello.restapi.dto;

import fish.payara.hello.GameState;

import java.util.List;

public class UpdateGameStates {

    private UserID userId;
    private List<GameState> selectedGameStates;

    public UserID getUserId() {
        return userId;
    }

    public void setUserId(UserID userId) {
        this.userId = userId;
    }

    public List<GameState> getSelectedGameStates() {
        return selectedGameStates;
    }

    public void setSelectedGameStates(List<GameState> selectedGameStates) {
        this.selectedGameStates = selectedGameStates;
    }
}
