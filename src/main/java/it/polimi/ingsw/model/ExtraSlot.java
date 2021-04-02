package it.polimi.ingsw.model;

import it.polimi.ingsw.model.benefit.Resource;
import it.polimi.ingsw.model.exceptions.*;


public class ExtraSlot {
    private Resource resourceExtra;
    //quantity is an integer between 1 and 2
    private int quantity;

    /**
     * Set the resource type that can be stored in this ExtraSlot
     */
    public ExtraSlot(Resource resourceExtra){

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
     * @throws NotEnoughSpaceException if the extra slot cannot contain all the resources
     */
    public void addResource(int quantity) throws NotEnoughSpaceException {

    }

    /**
     * Pick a resource from the slot
     * @param quantity
     * @throws NotEnoughResourcesException if the extra slot contains less resources of those you requested
     */
    public void removeResource(int quantity) throws NotEnoughResourcesException {

    }

    /**
     * Quantity of resources stored in this extra slot
     * @return
     */
    public int getQuantity(){
        return 0;
    }
}
