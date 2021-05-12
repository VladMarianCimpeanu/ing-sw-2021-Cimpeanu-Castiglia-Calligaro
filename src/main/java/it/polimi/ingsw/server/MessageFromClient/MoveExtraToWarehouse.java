package it.polimi.ingsw.server.MessageFromClient;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.TurnState;

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
