package it.polimi.ingsw.server.MessageFromClient;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.TurnState;

public class PlaceCard extends MessageFromClient {
    private int pos;
    @Override
    public void activate(Controller controller) {
        TurnState state = controller.getCurrentState();
        state.placeDevCard(pos);
    }
}
