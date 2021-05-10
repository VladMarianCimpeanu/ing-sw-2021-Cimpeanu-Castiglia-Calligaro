package it.polimi.ingsw.MessageToClient;

import it.polimi.ingsw.model.benefit.Resource;

import java.util.ArrayList;

public class ResourceToPut {
    private String type;
    private ArrayList<Resource> resources;

    public ResourceToPut(ArrayList<Resource> resources) {
        type = "ResourceToPut";
        this.resources = resources;
    }
}
