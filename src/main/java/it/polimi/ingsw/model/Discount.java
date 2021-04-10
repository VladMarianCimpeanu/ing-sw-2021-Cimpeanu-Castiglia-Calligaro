package it.polimi.ingsw.model;

import it.polimi.ingsw.model.benefit.Resource;

public class Discount {
    private Resource resource;

    /**
     * Set the resource type of the discount
     * @param resource specified resource can be discounted for a development card purchase
     */
    public Discount(Resource resource){
        this.resource = resource;
    }

    /**
     * A discount of 1 resource that can be used to buy development cards
     * @return the resource this discount worth
     */
    public Resource getResource() {
        return resource;
    }
}
