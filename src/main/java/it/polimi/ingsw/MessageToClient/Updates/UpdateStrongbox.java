package it.polimi.ingsw.MessageToClient.Updates;

import it.polimi.ingsw.MessageToClient.MessageToClient;
import it.polimi.ingsw.model.benefit.Resource;

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
