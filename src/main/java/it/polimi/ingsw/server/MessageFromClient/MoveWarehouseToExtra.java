package it.polimi.ingsw.server.MessageFromClient;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.ErrorMessage;
import it.polimi.ingsw.server.controller.states.TurnState;

/**
 * Message from client: it contains the shelf from where to take resources to move to the specified extra slot indicated
 * by the ID of the leader card that offers that power.
 */
public class MoveWarehouseToExtra extends MessageFromClient{
    private int shelf;
    private int leaderId;
    private int quantityToMove;

    @Override
    public void activate(Controller controller) {
        if(shelf == 0 || leaderId == 0 || quantityToMove == 0) {
            controller.sendError(ErrorMessage.invalidJson);
            return;
        }
        TurnState state = controller.getCurrentState();
        state.moveWarehouseToExtra(shelf, leaderId, quantityToMove);
    }
}
