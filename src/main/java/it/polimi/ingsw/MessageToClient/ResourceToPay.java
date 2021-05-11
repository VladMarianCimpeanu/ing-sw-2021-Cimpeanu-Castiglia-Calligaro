package it.polimi.ingsw.MessageToClient;

import it.polimi.ingsw.model.benefit.Resource;

import java.util.Map;

public class ResourceToPay implements MessageToClient{
    private String type;
    private Map<Resource, Integer> resources;

    public ResourceToPay(Map<Resource, Integer> resources) {
        type = "ResourceToPay";
        this.resources = resources;
    }
}
