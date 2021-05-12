package it.polimi.ingsw.client.MessageToServer;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.TurnState;
import it.polimi.ingsw.server.model.benefit.Resource;

public class TakeResPos extends MessageFromClient {
    private Resource resource;
    private String position;

    @Override
    public void activate(Controller controller) {
        TurnState state = controller.getCurrentState();
        state.pay(resource, position);
    }
}
