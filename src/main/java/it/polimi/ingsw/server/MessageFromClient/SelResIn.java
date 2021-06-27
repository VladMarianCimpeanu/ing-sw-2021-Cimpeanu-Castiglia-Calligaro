package it.polimi.ingsw.server.MessageFromClient;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.ErrorMessage;
import it.polimi.ingsw.server.controller.states.TurnState;
import it.polimi.ingsw.server.model.benefit.Resource;

/**
 * Message from client: it contains the input resources for base or extra production.
 */
public class SelResIn extends MessageFromClient {
    private Resource res1, res2;
    @Override
    public void activate(Controller controller) {
        if(res1 == null || res2 == null) {
            controller.sendError(ErrorMessage.invalidJson);
            return;
        }
        TurnState state = controller.getCurrentState();
        state.selectInput(res1, res2);
    }
}
