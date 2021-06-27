package it.polimi.ingsw.server.MessageFromClient;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.TurnState;

/**
 * Message from client: the client tries to end his turn.
 */
public class EndTurn extends MessageFromClient {
    @Override
    public void activate(Controller controller) {
        TurnState state = controller.getCurrentState();
        state.end();
    }
}
