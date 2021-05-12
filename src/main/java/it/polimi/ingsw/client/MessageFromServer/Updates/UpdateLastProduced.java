package it.polimi.ingsw.client.MessageFromServer.Updates;

import it.polimi.ingsw.client.MessageFromServer.MessageFromServer;
import it.polimi.ingsw.server.model.benefit.Resource;

import java.util.Map;

public class UpdateLastProduced extends MessageFromServer {
    private Map<Resource, Integer> resources;
    private String player;

    @Override
    public void activateMessage() {
        System.out.println(player + " has produced these resources " + resources.toString() + "\nin this turn.");
    }
}
