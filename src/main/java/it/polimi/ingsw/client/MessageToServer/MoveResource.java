package it.polimi.ingsw.client.MessageToServer;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.TurnState;

/**
 * Message to server: it contains the depot shelves indexes that must be switched.
 */
public class MoveResource implements MessageToServer {
    private String type;
    private int shelfFrom;
    private int shelfTo;

    public MoveResource(int shelfFrom, int shelfTo) {
        this.type = "MoveResource";
        this.shelfFrom = shelfFrom;
        this.shelfTo = shelfTo;
    }
}
