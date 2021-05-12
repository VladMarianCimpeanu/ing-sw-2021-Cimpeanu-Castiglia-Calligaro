package it.polimi.ingsw.server.MessageToClient;

import it.polimi.ingsw.server.model.benefit.Resource;

import java.util.ArrayList;

public class ResourceToPut implements MessageToClient{
    private String type;
    private ArrayList<Resource> resources;

    public ResourceToPut(ArrayList<Resource> resources) {
        type = "ResourceToPut";
        this.resources = resources;
    }
}
