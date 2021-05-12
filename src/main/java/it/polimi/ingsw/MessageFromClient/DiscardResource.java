package it.polimi.ingsw.MessageFromClient;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.states.TurnState;
import it.polimi.ingsw.model.benefit.Resource;

public class DiscardResource extends MessageFromClient {
    private Resource resource;
    @Override
    public void activate(Controller controller) {
        TurnState state = controller.getCurrentState();
        state.discardRes(resource);
    }
}
