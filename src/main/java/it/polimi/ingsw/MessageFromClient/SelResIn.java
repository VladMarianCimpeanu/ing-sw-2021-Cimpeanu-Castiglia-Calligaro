package it.polimi.ingsw.MessageFromClient;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.states.TurnState;
import it.polimi.ingsw.model.benefit.Resource;

public class SelResIn extends MessageFromClient {
    private Resource res1, res2;
    @Override
    public void activate(Controller controller) {
        TurnState state = controller.getCurrentState();
        state.selectInput(res1, res2);
    }
}
