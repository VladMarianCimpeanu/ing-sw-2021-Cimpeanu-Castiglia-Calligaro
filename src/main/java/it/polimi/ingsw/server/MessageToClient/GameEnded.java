package it.polimi.ingsw.server.MessageToClient;

import java.util.Map;

public class GameEnded implements MessageToClient{
    private String type;
    private Map<String, Integer> ranks;
    private Map<String, Integer> resources;

    public GameEnded(Map<String, Integer> ranks, Map<String, Integer> resources){
        this.ranks = ranks;
        this.resources = resources;
        this.type = "GameEnded";
    }
}
