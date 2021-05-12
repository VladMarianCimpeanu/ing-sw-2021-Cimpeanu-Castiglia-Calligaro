package it.polimi.ingsw.client.MessageFromServer;

import it.polimi.ingsw.server.model.benefit.Resource;

import java.util.Map;

public class ResourceToPay extends MessageFromServer {
    private Map<Resource, Integer> resources;

    @Override
    public void activateMessage() {
        System.out.println("Resources to pay:" + resources.toString());
    }
}
