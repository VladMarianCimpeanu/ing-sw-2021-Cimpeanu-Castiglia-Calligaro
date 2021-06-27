package it.polimi.ingsw.server.MessageFromClient;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.ErrorMessage;
import it.polimi.ingsw.server.controller.states.TurnState;

/**
 * Message from client: it contains the deck position to place the development card just bought.
 */
public class PlaceCard extends MessageFromClient {
    private int pos;
    @Override
    public void activate(Controller controller) {
        if(pos == 0) {
            controller.sendError(ErrorMessage.invalidJson);
            return;
        }
        TurnState state = controller.getCurrentState();
        state.placeDevCard(pos);
    }
}
