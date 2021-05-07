package it.polimi.ingsw.model;

import it.polimi.ingsw.model.benefit.Resource;

/**
 * Identify the Extra slots that can be added to the WareHouseDepot and can store only one defined type of resources
 * They can be created through the activation of some leader cards
 */

public class ExtraSlot {
    private final Resource resourceExtra;
    //quantity is an integer between 0 and 2
    private int quantity;
    private int ID;

    /**
     * Set the resource type that can be stored in this ExtraSlot
     */
    public ExtraSlot(Resource resourceExtra, int ID){
        this.resourceExtra = resourceExtra;
        quantity = 0;
        this.ID = ID;
    }

    /**
     * @return the type of resources that can be stored inside the slot
     */
    public Resource getResource(){
        return resourceExtra;
    }

    /**
     * Put resources in the slot
     * @param quantity number of resources needed to be put inside the slot
     * @return the number of resources that the method tried to put inside the slot but have been left over
     */
    public int addResource(int quantity){
        if(quantity < 0) return 0;
        this.quantity += quantity;
        int remain = 0;
        if(this.quantity > 2) {
            remain = this.quantity-2;
            this.quantity = 2;
        }
        return remain;
    }

    /**
     * Pick a resource from the slot
     * @param quantity number of resources
     * @return 0 if the slot has enough resources to be removed, otherwise returns the number of resources that remain after running out of resources inside the slot
     */
    public int removeResource(int quantity) {
        if(quantity < 0) return 0;
        if(quantity > this.quantity){
            int remain = quantity-this.quantity;
            this.quantity = 0;
            return remain;
        }
        this.quantity -= quantity;
        return 0;
    }

    /**
     * @return current quantity of resources stored inside the slot
     */
    public int getQuantity(){
        return quantity;
    }
}
