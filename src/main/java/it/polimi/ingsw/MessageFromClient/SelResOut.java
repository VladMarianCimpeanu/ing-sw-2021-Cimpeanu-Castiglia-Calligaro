package it.polimi.ingsw.MessageFromClient;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.states.TurnState;
import it.polimi.ingsw.model.benefit.Resource;

public class SelResOut extends MessageFromClient {
    private Resource resource;
    @Override
    public void activate(Controller controller) {
        TurnState state = controller.getCurrentState();
        state.selectOutput(resource);
    }
}
