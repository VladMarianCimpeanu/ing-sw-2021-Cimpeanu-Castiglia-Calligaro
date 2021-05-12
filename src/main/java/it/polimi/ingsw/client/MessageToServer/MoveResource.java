package it.polimi.ingsw.client.MessageToServer;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.TurnState;

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
