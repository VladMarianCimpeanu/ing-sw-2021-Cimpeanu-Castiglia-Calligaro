package it.polimi.ingsw.client.MessageToServer;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.TurnState;

public class MoveResource extends MessageFromClient {
    private int shelfFrom;
    private int shelfTo;
    @Override
    public void activate(Controller controller) {
        TurnState state = controller.getCurrentState();
        state.moveWarehouse(shelfFrom, shelfTo);
    }
}
