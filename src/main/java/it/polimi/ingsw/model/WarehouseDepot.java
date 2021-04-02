package it.polimi.ingsw.model;

import it.polimi.ingsw.model.benefit.Resource;
import it.polimi.ingsw.model.exceptions.*;

import java.util.ArrayList;

public class WarehouseDepot {
    private Resource firstShelf;
    private Resource secondShelf;
    private Resource thirdShelf;
    private int firstQuantity;
    private int secondQuantity;
    private int thirdQuantity;
    private int firstMax;
    private int secondMax;
    private int thirdMax;
    private ArrayList<ExtraSlot> extraSlotList;

    /**
     *
     * @param shelf: integer between 1 and 3
     * @return what kind of resource is contained in the shelf
     * @throws InvalidShelfPosition if shelf isn't an integer between 1 and 3
     */
    public Resource getShelfResource(int shelf) throws InvalidShelfPosition {
        return null;
    }

    /**
     *
     * @param shelf: integer between 1 and 3
     * @return the quantity of resources contained in the shelf
     * @throws InvalidShelfPosition if shelf isn't an integer between 1 and 3
     */
    public int getShelfQuantity(int shelf) throws InvalidShelfPosition {
        return 0;
    }

    /**
     * Move resources from a shelf to another shelf
     * @param fromShelf
     * @param toShelf
     * @throws InvalidShelfPosition if shelf isn't an integer between 1 and 3
     * @throws NotEnoughSpaceException if you try to put in a shelf more resources than its capacity
     */
    public void move(int fromShelf, int toShelf) throws InvalidShelfPosition, NotEnoughSpaceException {

    }

    /**
     * Add resources in a shelf
     * @param shelf: integer between 1 and 3
     * @param quantity must be less or equal than shelf
     * @throws InvalidShelfPosition if shelf isn't an integer between 1 and 3
     * @throws NotEnoughSpaceException if you try to put in a shelf more resources than its capacity
     */
    public void addResource(int shelf, int quantity) throws InvalidShelfPosition, NotEnoughSpaceException {

    }

    /**
     * Remove resources from a shelf
     * @param resource: resource type
     * @param quantity
     * @return the number of resources that you need to remove from the Strongbox
     */
    public int removeResource(Resource resource, int quantity) {
        return 0;
    }

    /**
     *
     * @return
     */
    public ArrayList<ExtraSlot> getExtraSlotList(){
        return null;
    }

    /**
     * Remove a resource from the extra slot
     * @param resource
     * @param quantity
     * @throws NotEnoughResourcesException if the extra slot contains less resources of those you requested
     * @throws MissingExtraSlot if there isn't an extra slot for the requested resource
     */
    private void removeExtraResource(Resource resource, int quantity) throws NotEnoughResourcesException, MissingExtraSlot {

    }

    /**
     * Add a resource to the extra slot
     * @param resource
     * @param quantity
     * @throws NotEnoughSpaceException if the extra slot cannot contain all the resources
     * @throws MissingExtraSlot if there isn't an extra slot for the requested resource
     */
    public void addExtraResource(Resource resource, int quantity) throws NotEnoughSpaceException, MissingExtraSlot {

    }

    /**
     * Get the quantity of resource stored in the extra slot
     * @param resource
     * @return 0 if there isn't an extra slot for the requested resource
     */
    private int getExtraQuantity(Resource resource){
        return 0;
    }

    /**
     * create a new ExtraSlot object and initialize it with the resource type
     * @param resourceExtra: resource that can be stored in the extra slot
     */
    public void addExtraSlot(Resource resourceExtra){

    }

    /**
     *
     * @param resource
     * @return quantity of the selected resource in stock, 0 if there are no resource in stock
     */
    public int getResourceQuantity(Resource resource){
        return 0;
    }
}
