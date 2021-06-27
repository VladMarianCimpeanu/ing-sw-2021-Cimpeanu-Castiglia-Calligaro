package it.polimi.ingsw.server.MessageToClient.Rejoin;

import it.polimi.ingsw.server.MessageToClient.MessageToClient;
import it.polimi.ingsw.server.model.benefit.Resource;

import java.util.Map;


/**
 * Message containing all the resources owned by a specified player in his strongbox.
 * This message is sent to the server when the player tries to join an already started game.
 */
public class RejoinStrongbox implements MessageToClient {
    private String type;
    private String player;
    private Map<Resource, Integer> deltaResources;

    public RejoinStrongbox(String player, Map<Resource, Integer> resources) {
        this.type = "RejoinStrongbox";
        this.player = player;
        this.deltaResources = resources;
    }
}
