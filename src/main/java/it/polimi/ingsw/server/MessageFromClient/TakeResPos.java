package it.polimi.ingsw.server.MessageFromClient;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.ErrorMessage;
import it.polimi.ingsw.server.controller.states.TurnState;
import it.polimi.ingsw.server.model.benefit.Resource;

/**
 * Message from client: it contains the selected resource for payment and from where to take: strongbox, extra, depot.
 */
public class TakeResPos extends MessageFromClient {
    private Resource resource;
    private String position;

    @Override
    public void activate(Controller controller) {
        if(resource == null || position == null) {
            controller.sendError(ErrorMessage.invalidJson);
            return;
        }
        TurnState state = controller.getCurrentState();
        state.pay(resource, position);
    }
}
