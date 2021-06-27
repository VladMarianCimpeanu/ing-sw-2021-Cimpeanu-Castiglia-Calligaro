package it.polimi.ingsw.server.MessageFromClient;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.ErrorMessage;
import it.polimi.ingsw.server.controller.states.TurnState;
import it.polimi.ingsw.server.model.benefit.Resource;

/**
 * Message: it contains the resource obtained by the market to discard.
 */
public class DiscardResource extends MessageFromClient {
    private Resource resource;
    @Override
    public void activate(Controller controller) {
        if(resource == null) {
            controller.sendError(ErrorMessage.invalidJson);
            return;
        }
        TurnState state = controller.getCurrentState();
        state.discardRes(resource);
    }
}
