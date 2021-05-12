package it.polimi.ingsw.server.MessageFromClient;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.TurnState;

public class Market extends MessageFromClient {
    private String direction;
    private int position;
    @Override
    public void activate(Controller controller) {
        TurnState state = controller.getCurrentState();
        state.goToMarket(direction, position);
    }
}
