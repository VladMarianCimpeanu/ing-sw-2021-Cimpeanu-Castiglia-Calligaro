package it.polimi.ingsw.model;

import it.polimi.ingsw.model.benefit.Resource;
import it.polimi.ingsw.model.exceptions.*;


public class ExtraSlot {
    private Resource resourceExtra;
    //quantity is an integer between 0 and 2
    private int quantity;

    /**
     * Set the resource type that can be stored in this ExtraSlot
     */
    public ExtraSlot(Resource resourceExtra){
        this.resourceExtra = resourceExtra;
        quantity = 0;
    }

    /**
     * Check what kind of resource can be stored
     * @return
     */
    public Resource getResource(){
        return null;
    }

    /**
     * Put a resource in the slot
     * @param quantity
     */
    public int addResource(int quantity) {
        return 0;
    }

    /**
     * Pick a resource from the slot
     * @param quantity
     */
    public int removeResource(int quantity) {
        return 0;
    }

    /**
     * Quantity of resources stored in this extra slot
     * @return
     */
    public int getQuantity(){
        return 0;
    }
}
