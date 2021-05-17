package it.polimi.ingsw.client.MessageFromServer;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.server.model.benefit.Resource;

import java.util.ArrayList;

public class ResourceToPut extends MessageFromServer {
    private ArrayList<Resource> resources;


    @Override
    public void activateMessage(Client client) {
        System.out.println("Resources received from market: " + resources.toString());
    }
}
