package it.polimi.ingsw.server.MessageFromClient;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.TurnState;
import it.polimi.ingsw.server.model.benefit.Resource;

public class DiscardResource extends MessageFromClient {
    private Resource resource;
    @Override
    public void activate(Controller controller) {
        if(resource == null) {
            controller.sendError("invalidJson");
            return;
        }
        TurnState state = controller.getCurrentState();
        state.discardRes(resource);
    }
}
