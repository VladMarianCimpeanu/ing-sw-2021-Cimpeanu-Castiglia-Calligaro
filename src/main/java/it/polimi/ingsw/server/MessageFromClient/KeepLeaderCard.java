package it.polimi.ingsw.server.MessageFromClient;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.TurnState;

public class KeepLeaderCard extends MessageFromClient {
    private int id1, id2;
    private String nickname;
    @Override
    public void activate(Controller controller) {
        if(id1 == 0 || id2 == 0 || nickname == null) {
            controller.sendError("invalidJson");
            return;
        }
        TurnState state = controller.getCurrentState();
        state.keepLeaderCards(nickname, id1, id2);
    }
}
