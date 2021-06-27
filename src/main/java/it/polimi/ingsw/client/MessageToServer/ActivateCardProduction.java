package it.polimi.ingsw.client.MessageToServer;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.TurnState;

/**
 * Message to server: it contains the deck index of the development card selected by the player in order to use
 * its production.
 */
public class ActivateCardProduction implements MessageToServer {
    private String type;
    private int index;

    public ActivateCardProduction(int index) {
        this.type = "ActivateCardProduction";
        this.index = index;
    }
}
