package it.polimi.ingsw.MessageFromClient;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.states.TurnState;

public class MoveResource extends MessageFromClient {
    private int shelfFrom;
    private int shelfTo;
    @Override
    public void activate(Controller controller) {
        TurnState state = controller.getCurrentState();
        state.moveWarehouse(shelfFrom, shelfTo);
    }
}
