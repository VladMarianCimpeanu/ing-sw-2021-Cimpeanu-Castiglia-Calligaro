package it.polimi.ingsw.server.MessageFromClient;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.ErrorMessage;
import it.polimi.ingsw.server.controller.states.TurnState;

/**
 * Message from client: it contains the deck index of the development card selected by the player in order to use
 * its production.
 */
public class ActivateCardProduction extends MessageFromClient {
    private int index;
    @Override
    public void activate(Controller controller) {
        if(index == 0) {
            controller.sendError(ErrorMessage.invalidJson);
            return;
        }
        TurnState state = controller.getCurrentState();
        state.activateDevCard(index);
    }
}
