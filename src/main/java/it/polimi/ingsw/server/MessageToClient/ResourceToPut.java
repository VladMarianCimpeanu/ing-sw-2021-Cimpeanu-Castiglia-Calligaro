package it.polimi.ingsw.server.MessageToClient;

import it.polimi.ingsw.server.model.benefit.Resource;

import java.util.ArrayList;

/**
 * Message to client: it contains the resources that remains to take from the market.
 */
public class ResourceToPut implements MessageToClient{
    private String type;
    private ArrayList<Resource> resources;

    public ResourceToPut(ArrayList<Resource> resources) {
        type = "ResourceToPut";
        this.resources = resources;
    }
}
