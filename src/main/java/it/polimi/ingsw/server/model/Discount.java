package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.benefit.Resource;

/**
 * This class represents a discount that a player can use to buy a new Development card.
 * The discount is represented by a specific resource.
 */
public class Discount {
    private Resource resource;
    private int ID;

    /**
     * Set the resource type of the discount
     * @param resource specified resource can be discounted for a development card purchase
     */
    public Discount(Resource resource, int ID){
        this.resource = resource;
        this.ID = ID;
    }

    /**
     * A discount of 1 resource that can be used to buy development cards
     * @return the resource this discount worth
     */
    public Resource getResource() {
        return resource;
    }

    public int getID() {
        return ID;
    }
}
