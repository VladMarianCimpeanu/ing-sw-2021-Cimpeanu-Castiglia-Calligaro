package it.polimi.ingsw.server.MessageToClient.Updates;

import it.polimi.ingsw.server.MessageToClient.MessageToClient;
import it.polimi.ingsw.server.model.benefit.Resource;

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
