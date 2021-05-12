package it.polimi.ingsw.MessageFromClient;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.states.TurnState;

public class MoveExtraToWarehouse extends MessageFromClient{
    private int shelfFrom;
    private int leaderId;
    private int quantity;

    @Override
    public void activate(Controller controller) {
        TurnState state = controller.getCurrentState();
        state.moveExtraToWarehouse(shelfFrom, leaderId, quantity);
    }
}
