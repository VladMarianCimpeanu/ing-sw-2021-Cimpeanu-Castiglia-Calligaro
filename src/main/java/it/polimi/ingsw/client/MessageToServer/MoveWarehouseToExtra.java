package it.polimi.ingsw.client.MessageToServer;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.TurnState;

public class MoveWarehouseToExtra implements MessageToServer {
    private String type;
    private int shelf;
    private int leaderId;
    private int quantityToMove;

    public MoveWarehouseToExtra(int shelf, int leaderId, int quantityToMove) {
        this.type = "MoveWarehouseToExtra";
        this.shelf = shelf;
        this.leaderId = leaderId;
        this.quantityToMove = quantityToMove;
    }
}
