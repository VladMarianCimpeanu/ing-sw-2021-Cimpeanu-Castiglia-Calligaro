package it.polimi.ingsw.client.MessageFromServer.Updates;

import it.polimi.ingsw.client.MessageFromServer.MessageToClient;
import it.polimi.ingsw.server.model.benefit.Resource;

import java.util.Map;

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
