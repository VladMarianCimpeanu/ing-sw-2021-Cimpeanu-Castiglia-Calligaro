package it.polimi.ingsw.server.MessageFromClient;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.TurnState;
import it.polimi.ingsw.server.model.Color;

import java.util.ArrayList;

public class BuyDevCard extends MessageFromClient {
    private int level;
    private Color color;
    private ArrayList<Integer> discountsID;
    @Override
    public void activate(Controller controller) {
        if(color == null || discountsID == null || level == 0) {
            controller.sendError("invalidJson");
            return;
        }
        TurnState state = controller.getCurrentState();
        state.buyDevCard(color, level, discountsID);
    }
}
