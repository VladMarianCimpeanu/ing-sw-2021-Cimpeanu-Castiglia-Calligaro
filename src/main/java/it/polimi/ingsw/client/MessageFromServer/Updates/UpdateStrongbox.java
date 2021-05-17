package it.polimi.ingsw.client.MessageFromServer.Updates;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.MessageFromServer.MessageFromServer;
import it.polimi.ingsw.server.model.benefit.Resource;

import java.util.Map;

public class UpdateStrongbox extends MessageFromServer {
    private String player;
    private Map<Resource, Integer> deltaResources;

    @Override
    public void activateMessage(Client client) {

        System.out.println(player + "changed the following resources in his strongbox: " + deltaResources.toString());
    }
}
