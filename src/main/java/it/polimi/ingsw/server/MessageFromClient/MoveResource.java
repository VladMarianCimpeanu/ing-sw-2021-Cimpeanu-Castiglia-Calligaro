package it.polimi.ingsw.server.MessageFromClient;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.ErrorMessage;
import it.polimi.ingsw.server.controller.states.TurnState;

public class MoveResource extends MessageFromClient {
    private int shelfFrom;
    private int shelfTo;
    @Override
    public void activate(Controller controller) {
        if(shelfFrom == 0 || shelfTo == 0) {
            controller.sendError(ErrorMessage.invalidJson);
            return;
        }
        TurnState state = controller.getCurrentState();
        state.moveWarehouse(shelfFrom, shelfTo);
    }
}
