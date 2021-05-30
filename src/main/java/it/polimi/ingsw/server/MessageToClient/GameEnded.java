package it.polimi.ingsw.server.MessageToClient;

import java.util.Map;

public class GameEnded implements MessageToClient{
    private String type;
    private Map<String, Integer> ranks;

    public GameEnded(Map<String, Integer> ranks){
        this.ranks = ranks;
        this.type = "GameEnded";
    }
}
