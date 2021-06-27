package it.polimi.ingsw.server.MessageFromClient;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.TurnState;

/**
 * Message from client: it asks the server to activate the selected production.
 */
public class ActivateProduction extends MessageFromClient {
    @Override
    public void activate(Controller controller) {
        TurnState state = controller.getCurrentState();
        state.activateProduction();
    }
}
