package it.polimi.ingsw.client.MessageToServer;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.TurnState;

/**
 * Message to server: it contains the leader card's ID to discard.
 */
public class DiscardLeaderCard implements MessageToServer {
    private String type;
    private int id;

    public DiscardLeaderCard(int id) {
        this.type = "DiscardLeaderCard";
        this.id = id;
    }
}
