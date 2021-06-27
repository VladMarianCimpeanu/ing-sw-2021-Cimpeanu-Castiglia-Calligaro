package it.polimi.ingsw.server.MessageToClient.Rejoin;

import it.polimi.ingsw.server.MessageToClient.MessageToClient;
import it.polimi.ingsw.server.model.benefit.Resource;

/**
 * Message containing all the resources owned by a specified player in his extra slots.
 * This message is sent to the client when the player tries to join an already started game.
 */
public class RejoinExtraSlot implements MessageToClient {
    private String type;
    private String player;
    private Resource resource;
    private int id;
    private int quantity;

    public RejoinExtraSlot(String player, Resource resource, int id, int quantity) {
        this.type = "RejoinExtraSlot";
        this.player = player;
        this.resource = resource;
        this.id = id;
        this.quantity = quantity;
    }
}
