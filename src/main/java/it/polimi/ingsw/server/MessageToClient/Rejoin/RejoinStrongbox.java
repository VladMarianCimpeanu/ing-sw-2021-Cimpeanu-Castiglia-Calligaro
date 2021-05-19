package it.polimi.ingsw.server.MessageToClient.Rejoin;

import it.polimi.ingsw.server.MessageToClient.MessageToClient;
import it.polimi.ingsw.server.model.benefit.Resource;

import java.util.Map;

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
