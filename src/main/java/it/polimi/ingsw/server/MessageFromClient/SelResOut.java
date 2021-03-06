package it.polimi.ingsw.server.MessageFromClient;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.ErrorMessage;
import it.polimi.ingsw.server.controller.states.TurnState;
import it.polimi.ingsw.server.model.benefit.Resource;

/**
 * Message from client: it contains the desired output resources from a base production.
 */
public class SelResOut extends MessageFromClient {
    private Resource resource;
    @Override
    public void activate(Controller controller) {
        if(resource == null) {
            controller.sendError(ErrorMessage.invalidJson);
            return;
        }
        TurnState state = controller.getCurrentState();
        state.selectOutput(resource);
    }
}
