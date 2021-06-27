package it.polimi.ingsw.server.MessageFromClient;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.ErrorMessage;
import it.polimi.ingsw.server.controller.states.TurnState;

/**
 * Message from client: it contains the row/ column of the market selected by the client.
 */
public class Market extends MessageFromClient {
    private String direction;
    private int position;
    @Override
    public void activate(Controller controller) {
        if(position == 0 || direction == null) {
            controller.sendError(ErrorMessage.invalidJson);
            return;
        }
        TurnState state = controller.getCurrentState();
        state.goToMarket(direction, position - 1);
    }
}
