package it.polimi.ingsw.client.MessageToServer;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.TurnState;

public class MoveExtraToWarehouse implements MessageToServer {
    private String type;
    private int shelfFrom;
    private int leaderId;
    private int quantity;

    public MoveExtraToWarehouse(int shelfFrom, int leaderId, int quantity) {
        this.type = "MoveExtraToWarehouse";
        this.shelfFrom = shelfFrom;
        this.leaderId = leaderId;
        this.quantity = quantity;
    }
}
