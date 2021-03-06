package it.polimi.ingsw.server.MessageToClient.Updates;

import it.polimi.ingsw.server.MessageToClient.MessageToClient;
import it.polimi.ingsw.server.model.benefit.Resource;

import java.util.Map;

/**
 * Message to client: it contains all the resources produced by a specified player.
 */
public class UpdateLastProduced implements MessageToClient {
    private String type;
    private Map<Resource, Integer> resources;
    private String player;

    public UpdateLastProduced(String player, Map<Resource, Integer> resources) {
        this.type = "UpdateLastProduced";
        this.resources = resources;
        this.player = player;
    }
}
