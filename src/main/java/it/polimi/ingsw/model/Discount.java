package it.polimi.ingsw.model;

import it.polimi.ingsw.model.benefit.Resource;

public class Discount {
    private Resource resource;

    /**
     * Set the resource type of the discount
     * @param resource
     */
    public Discount(Resource resource){

    }

    /**
     * A discount of 1 resource that can be used to buy development cards
     * @return
     */
    public Resource getResource() {
        return resource;
    }
}
