package it.polimi.ingsw.MessageFromClient;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.states.TurnState;

public class KeepLeaderCard extends MessageFromClient {
    private int id1, id2;
    private String nickname;
    @Override
    public void activate(Controller controller) {
        TurnState state = controller.getCurrentState();
        state.keepLeaderCards(nickname, id1, id2);
    }
}
