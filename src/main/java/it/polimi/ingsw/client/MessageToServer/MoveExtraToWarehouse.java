package it.polimi.ingsw.client.MessageToServer;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.TurnState;

public class MoveExtraToWarehouse implements MessageToServer {
    private String type;
    private int shelf;
    private int leaderId;
    private int quantity;

    public MoveExtraToWarehouse(int shelf, int leaderId, int quantity) {
        this.type = "MoveExtraToWarehouse";
        this.shelf = shelf;
        this.leaderId = leaderId;
        this.quantity = quantity;
    }
}
