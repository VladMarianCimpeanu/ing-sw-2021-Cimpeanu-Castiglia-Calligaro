package it.polimi.ingsw.server.MessageToClient.Rejoin;

import it.polimi.ingsw.server.MessageToClient.MessageToClient;
import it.polimi.ingsw.server.model.benefit.Resource;

/**
 * Message containing all the resources owned by a specified player in his depot.
 * This message is sent to the client when the player tries to join an already started game.
 */
public class RejoinDepot implements MessageToClient {
    private String type;
    private String player;
    private int shelf;
    private Resource resource;
    private int quantity;

    public RejoinDepot(String player, int shelf, Resource resource, int quantity) {
        this.type = "RejoinDepot";
        this.player = player;
        this.shelf = shelf;
        this.resource = resource;
        this.quantity = quantity;
    }
}
