package it.polimi.ingsw.server.MessageToClient.Updates;

import it.polimi.ingsw.server.MessageToClient.MessageToClient;
import it.polimi.ingsw.server.model.benefit.Resource;

public class UpdateExtraSlot implements MessageToClient {
    private String type;
    private String player;
    private Resource resource;
    private int id;
    private int quantity;

    public UpdateExtraSlot(String player, Resource resource, int id, int quantity) {
        this.type = "UpdateExtraSlot";
        this.player = player;
        this.resource = resource;
        this.id = id;
        this.quantity = quantity;
    }
}
