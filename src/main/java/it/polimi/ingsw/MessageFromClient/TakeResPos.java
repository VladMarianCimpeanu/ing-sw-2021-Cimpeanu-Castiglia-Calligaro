package it.polimi.ingsw.MessageFromClient;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.states.TurnState;
import it.polimi.ingsw.model.benefit.Resource;

public class TakeResPos extends MessageFromClient {
    private Resource resource;
    private String position;

    @Override
    public void activate(Controller controller) {
        TurnState state = controller.getCurrentState();
        state.pay(resource, position);
    }
}
