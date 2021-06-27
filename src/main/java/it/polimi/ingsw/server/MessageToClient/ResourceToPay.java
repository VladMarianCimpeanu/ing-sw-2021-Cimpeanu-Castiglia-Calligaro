package it.polimi.ingsw.server.MessageToClient;

import it.polimi.ingsw.server.model.benefit.Resource;

import java.util.Map;

/**
 * Message to client: it contains the resources that remains to pay by the client.
 */
public class ResourceToPay implements MessageToClient{
    private String type;
    private Map<Resource, Integer> resources;

    public ResourceToPay(Map<Resource, Integer> resources) {
        type = "ResourceToPay";
        this.resources = resources;
    }
}
