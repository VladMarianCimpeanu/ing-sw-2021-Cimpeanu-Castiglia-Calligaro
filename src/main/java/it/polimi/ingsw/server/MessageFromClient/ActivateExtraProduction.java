package it.polimi.ingsw.server.MessageFromClient;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.ErrorMessage;
import it.polimi.ingsw.server.controller.states.TurnState;

/**
 * Message from client: it contains the id of the leader card selected in order to use its extra production power.
 */
public class ActivateExtraProduction extends MessageFromClient {
    private int id;
    @Override
    public void activate(Controller controller) {
        if(id == 0) {
            controller.sendError(ErrorMessage.invalidJson);
            return;
        }
        TurnState state = controller.getCurrentState();
        state.activateExtra(id);
    }
}
