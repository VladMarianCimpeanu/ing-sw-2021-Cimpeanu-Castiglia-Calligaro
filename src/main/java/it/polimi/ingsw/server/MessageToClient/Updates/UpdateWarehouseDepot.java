package it.polimi.ingsw.server.MessageToClient.Updates;

import it.polimi.ingsw.server.MessageToClient.MessageToClient;
import it.polimi.ingsw.server.model.benefit.Resource;

/**
 * Message to client: it contains the shelf of a specified player that has been changed. It indicates the number
 * and which kind of resources are placed in the specified shelf.
 */
public class UpdateWarehouseDepot implements MessageToClient {
    private String type;
    private String player;
    private int shelf;
    private Resource resource;
    private int quantity;

    public UpdateWarehouseDepot(String player, int shelf, Resource resource, int quantity) {
        this.type = "UpdateWarehouseDepot";
        this.player = player;
        this.shelf = shelf;
        this.resource = resource;
        this.quantity = quantity;
    }
}
