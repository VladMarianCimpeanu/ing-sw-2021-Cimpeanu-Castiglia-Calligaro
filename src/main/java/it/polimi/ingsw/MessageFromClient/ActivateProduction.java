package it.polimi.ingsw.MessageFromClient;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.states.TurnState;

public class ActivateProduction extends MessageFromClient {
    @Override
    public void activate(Controller controller) {
        TurnState state = controller.getCurrentState();
        state.activateProduction();
    }
}
