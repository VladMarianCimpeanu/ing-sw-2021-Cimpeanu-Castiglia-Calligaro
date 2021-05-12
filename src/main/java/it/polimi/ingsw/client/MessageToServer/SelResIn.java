package it.polimi.ingsw.client.MessageToServer;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.TurnState;
import it.polimi.ingsw.server.model.benefit.Resource;

public class SelResIn extends MessageFromClient {
    private Resource res1, res2;
    @Override
    public void activate(Controller controller) {
        TurnState state = controller.getCurrentState();
        state.selectInput(res1, res2);
    }
}
