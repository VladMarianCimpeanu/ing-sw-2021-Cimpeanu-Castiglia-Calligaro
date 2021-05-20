package it.polimi.ingsw.server.MessageFromClient;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.ErrorMessage;
import it.polimi.ingsw.server.controller.states.TurnState;

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
