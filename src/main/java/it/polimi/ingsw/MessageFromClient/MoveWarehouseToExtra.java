package it.polimi.ingsw.MessageFromClient;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.states.TurnState;

public class MoveWarehouseToExtra extends MessageFromClient{
    private int shelf;
    private int leaderId;
    private int quantityToMove;

    @Override
    public void activate(Controller controller) {
        TurnState state = controller.getCurrentState();
        state.moveWarehouseToExtra(shelf, leaderId, quantityToMove);
    }
}
