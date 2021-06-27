package it.polimi.ingsw.server.MessageToClient;

import java.util.Map;

/**
 * Message to client: it notifies that the game is ended.
 * It contains the points earned by each player and the number of resources owned by each player.
 */
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
