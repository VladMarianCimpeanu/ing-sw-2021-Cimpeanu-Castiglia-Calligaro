package it.polimi.ingsw.client.MessageToServer;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.TurnState;

/**
 * Message to server: it contains the shelf from where to take resources to move to the specified extra slot indicated
 * by the ID of the leader card that offers that power.
 */
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
