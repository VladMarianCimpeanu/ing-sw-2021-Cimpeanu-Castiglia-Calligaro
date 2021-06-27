package it.polimi.ingsw.client.MessageToServer;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.TurnState;

/**
 * Message to server: it contains the id of a market strategy to use at the market to convert white marbles.
 */
public class Strategy implements MessageToServer {
    private String type;
    private int id;

    public Strategy(int id) {
        this.type = "Strategy";
        this.id = id;
    }
}
