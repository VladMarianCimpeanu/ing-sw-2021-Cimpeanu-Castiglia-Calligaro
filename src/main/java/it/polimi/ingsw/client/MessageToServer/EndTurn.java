package it.polimi.ingsw.client.MessageToServer;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.TurnState;

public class EndTurn extends MessageFromClient {
    @Override
    public void activate(Controller controller) {
        TurnState state = controller.getCurrentState();
        state.end();
    }
}
