package it.polimi.ingsw.MessageFromClient;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.states.TurnState;

public class Strategy extends MessageFromClient {
    private int id;
    @Override
    public void activate(Controller controller) {
        TurnState state = controller.getCurrentState();
        state.addStrategy(id);
    }
}
