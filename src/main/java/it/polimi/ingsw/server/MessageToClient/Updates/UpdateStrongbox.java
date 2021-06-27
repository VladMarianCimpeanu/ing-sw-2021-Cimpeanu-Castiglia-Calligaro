package it.polimi.ingsw.server.MessageToClient.Updates;

import it.polimi.ingsw.server.MessageToClient.MessageToClient;
import it.polimi.ingsw.server.model.benefit.Resource;

import java.util.Map;

/**
 * Message to client: contains all the resources owned by a specified player in his strongbox.
 */
public class UpdateStrongbox implements MessageToClient {
    private String type;
    private String player;
    private Map<Resource, Integer> deltaResources;

    public UpdateStrongbox(String player, Map<Resource, Integer> resources) {
        this.type = "UpdateStrongbox";
        this.player = player;
        this.deltaResources = resources;
    }
}
