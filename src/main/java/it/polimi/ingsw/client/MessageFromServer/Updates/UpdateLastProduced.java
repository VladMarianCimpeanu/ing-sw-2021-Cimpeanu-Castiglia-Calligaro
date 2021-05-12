package it.polimi.ingsw.client.MessageFromServer.Updates;

import it.polimi.ingsw.client.MessageFromServer.MessageToClient;
import it.polimi.ingsw.server.model.benefit.Resource;

import java.util.Map;

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
