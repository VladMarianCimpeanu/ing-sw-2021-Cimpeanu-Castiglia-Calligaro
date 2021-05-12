package it.polimi.ingsw.client.MessageToServer;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.TurnState;

public class KeepLeaderCard extends MessageFromClient {
    private int id1, id2;
    private String nickname;
    @Override
    public void activate(Controller controller) {
        TurnState state = controller.getCurrentState();
        state.keepLeaderCards(nickname, id1, id2);
    }
}
