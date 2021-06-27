package it.polimi.ingsw.server.MessageFromClient;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.TurnState;

/**
 * Message from client: it notifies the server that the client has just select to use a base production power.
 */
public class ActivateBaseProduction extends MessageFromClient {

    @Override
    public void activate(Controller controller) {
        TurnState state = controller.getCurrentState();
        state.activateBase();
    }
}
