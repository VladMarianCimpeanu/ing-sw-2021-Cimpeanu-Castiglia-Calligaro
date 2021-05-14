package it.polimi.ingsw.server.MessageFromClient;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.TurnState;

public class MoveExtraToWarehouse extends MessageFromClient{
    private int shelf;
    private int leaderId;
    private int quantity;

    @Override
    public void activate(Controller controller) {
        if(shelf == 0 || leaderId == 0 || quantity == 0) {
            controller.sendError("invalidJson");
            return;
        }
        TurnState state = controller.getCurrentState();
        state.moveExtraToWarehouse(shelf, leaderId, quantity);
    }
}
