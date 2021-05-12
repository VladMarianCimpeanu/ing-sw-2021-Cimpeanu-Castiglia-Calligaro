package it.polimi.ingsw.server.MessageFromClient;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.TurnState;

public class ActivateCardProduction extends MessageFromClient {
    private int index;
    @Override
    public void activate(Controller controller) {
        if(index == 0) {
            controller.sendError("invalidJson");
            return;
        }
        TurnState state = controller.getCurrentState();
        state.activateDevCard(index);
    }
}
