package it.polimi.ingsw.client.MessageToServer;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.TurnState;

public class ActivateExtraProduction extends MessageFromClient {
    private int id;
    @Override
    public void activate(Controller controller) {
        TurnState state = controller.getCurrentState();
        state.activateExtra(id);
    }
}
