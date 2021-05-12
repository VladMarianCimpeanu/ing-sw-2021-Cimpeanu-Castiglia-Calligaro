package it.polimi.ingsw.server.MessageFromClient;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.TurnState;

public class ActivateBaseProduction extends MessageFromClient {

    @Override
    public void activate(Controller controller) {
        TurnState state = controller.getCurrentState();
        state.activateBase();
    }
}
