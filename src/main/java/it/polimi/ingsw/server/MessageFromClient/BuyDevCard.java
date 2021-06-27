package it.polimi.ingsw.server.MessageFromClient;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.ErrorMessage;
import it.polimi.ingsw.server.controller.states.TurnState;
import it.polimi.ingsw.server.model.Color;

import java.util.ArrayList;

/**
 * Message from client: it contains the level, color and IDs used to buy a certain development card.
 */
public class BuyDevCard extends MessageFromClient {
    private int level;
    private Color color;
    private ArrayList<Integer> discountsID;
    @Override
    public void activate(Controller controller) {
        if(color == null || discountsID == null || level == 0) {
            controller.sendError(ErrorMessage.invalidJson);
            return;
        }
        TurnState state = controller.getCurrentState();
        state.buyDevCard(color, level, discountsID);
    }
}
