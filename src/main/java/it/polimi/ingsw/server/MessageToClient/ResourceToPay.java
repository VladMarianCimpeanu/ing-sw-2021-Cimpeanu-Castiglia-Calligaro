package it.polimi.ingsw.server.MessageToClient;

import it.polimi.ingsw.server.model.benefit.Resource;

import java.util.Map;

public class ResourceToPay implements MessageToClient{
    private String type;
    private Map<Resource, Integer> resources;

    public ResourceToPay(Map<Resource, Integer> resources) {
        type = "ResourceToPay";
        this.resources = resources;
    }
}
