package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.controller.VirtualView;
import it.polimi.ingsw.server.model.benefit.Resource;
import it.polimi.ingsw.server.model.exceptions.*;

import java.util.ArrayList;

/**
 * Identify Player's WareHouseDepot, composed of 3 shelves and other possible extra slots, gained from the activation of some leader cards
 * Each player has the reference to a unique and personal WareHouseDepot
 * Here can be store resources coming from the market
 * The resources store here can be used to buy development cards, activate productions and activate leader cards
 */

public class WarehouseDepot {
    private Resource firstShelf;
    private Resource secondShelf;
    private Resource thirdShelf;
    private int firstQuantity;
    private int secondQuantity;
    private int thirdQuantity;
    private final ArrayList<ExtraSlot> extraSlotList;
    private VirtualView virtualView;

    public WarehouseDepot() {
        //arbitrary values
        firstShelf = Resource.COIN;
        secondShelf = Resource.COIN;
        thirdShelf = Resource.COIN;
        firstQuantity = 0;
        secondQuantity = 0;
        thirdQuantity = 0;
        extraSlotList = new ArrayList<>();
    }

    /**
     * @param id Identifier of the leadercard associated with the extra slot
     * @return the extra slot selected by id
     * @throws InvalidIDExcpetion if ID is not found in the extra slot list
     */
    public ExtraSlot getExtraSlot(int id) throws InvalidIDExcpetion{
        for(ExtraSlot e: extraSlotList){
            if (e.getID() == id) return e;
        }
        throw new InvalidIDExcpetion();
    }

    /**
     *
     * @param shelf: integer between 1 and 3
     * @return what kind of resource is contained in the shelf
     * @throws InvalidShelfPosition if shelf isn't an integer between 1 and 3
     */
    public Resource getShelfResource(int shelf) throws InvalidShelfPosition {
        switch(shelf){
            case 1:
                return firstShelf;
            case 2:
                return secondShelf;
            case 3:
                return thirdShelf;
            default:
                throw new InvalidShelfPosition();
        }
    }

    /**
     *
     * @param shelf: integer between 1 and 3
     * @return the quantity of resources contained in the shelf
     * @throws InvalidShelfPosition if shelf isn't an integer between 1 and 3
     */
    public int getShelfQuantity(int shelf) throws InvalidShelfPosition {
        switch(shelf){
            case 1:
                return firstQuantity;
            case 2:
                return secondQuantity;
            case 3:
                return thirdQuantity;
            default:
                throw new InvalidShelfPosition();
        }
    }

    public int getResourceShelf(Resource resource) throws InvalidResourceException{
        try {
            for(int i = 1; i<4; i++)
                if(getShelfResource(i) == resource && getShelfQuantity(i) >= 0)
                    return i;
        } catch (InvalidShelfPosition invalidShelfPosition) {
            invalidShelfPosition.printStackTrace();
        }
        throw new InvalidResourceException();
    }

    public int getFreeShelf(int shelfMax) throws InvalidShelfPosition{
        if(shelfMax <1 || shelfMax >3) throw new InvalidShelfPosition();
        for(int i = shelfMax; i>0; i--)
            if(getShelfQuantity(i) == 0)
                return i;
        throw new InvalidShelfPosition();
    }

    /**
     * Move resources from a shelf to another shelf
     * @param fromShelf first shelf's number
     * @param toShelf second shelf's number
     * @throws InvalidShelfPosition if shelf isn't an integer between 1 and 3
     * @throws NotEnoughSpaceException if you try to put in a shelf more resources than its capacity
     */
    public void move(int fromShelf, int toShelf) throws InvalidShelfPosition, NotEnoughSpaceException {
        if(fromShelf < 1 || fromShelf > 3 || toShelf < 1 || toShelf > 3) throw new InvalidShelfPosition();
        if(fromShelf == toShelf) return;
        int max, min;
        if(fromShelf > toShelf){
            max = fromShelf;
            min = toShelf;
        }else{
            max = toShelf;
            min = fromShelf;
        }
        if(getShelfQuantity(max) > min) throw new NotEnoughSpaceException();
        else{
            Resource temp_resource = getShelfResource(max);
            int temp_quantity = getShelfQuantity(max);
            switch(max){
                case 2:
                    secondQuantity = getShelfQuantity(min);
                    secondShelf = getShelfResource(min);
                    virtualView.updateWarehouseDepot(2, secondShelf, secondQuantity);
                    break;
                case 3:
                    thirdQuantity = getShelfQuantity(min);
                    thirdShelf = getShelfResource(min);
                    virtualView.updateWarehouseDepot(3, thirdShelf, thirdQuantity);
                    break;
            }
            switch (min){
                case 1:
                    firstQuantity = temp_quantity;
                    firstShelf = temp_resource;
                    virtualView.updateWarehouseDepot(1, firstShelf, firstQuantity);
                    break;
                case 2:
                    secondQuantity = temp_quantity;
                    secondShelf = temp_resource;
                    virtualView.updateWarehouseDepot(2, secondShelf, secondQuantity);
                    break;
            }
        }
    }

    /**
     * Add resources in a shelf
     * @param shelf integer between 1 and 3
     * @param quantity must be less or equal than shelf
     * @param resource specific resource to be added to the specific shelf
     * @throws InvalidShelfPosition if shelf isn't an integer between 1 and 3
     * @throws ExistingResourceException if the selected resource already exists in another shelf
     * @throws InvalidResourceException if the selected resource is different from the resources already stored at the same shelf
     */
    public int addResource(int shelf, int quantity, Resource resource) throws InvalidShelfPosition, ExistingResourceException,  InvalidResourceException{
        if(shelf < 1 || shelf > 3) throw new InvalidShelfPosition();
        if(quantity <0) return 0;
        if(!getShelfResource(shelf).equals(resource) && getShelfQuantity(shelf) != 0) throw new InvalidResourceException();
        for(int i = 1; i<= 3; i++) {
            if (shelf == i) continue;
            if (getShelfQuantity(i) == 0) continue;
            if(getShelfResource(i).equals(resource))
                throw new ExistingResourceException();
        }
        if(getShelfQuantity(shelf) == 0){
            switch(shelf){
                case 1:
                    firstShelf = resource;
                    break;
                case 2:
                    secondShelf = resource;
                    break;
                case 3:
                    thirdShelf = resource;
                    break;
            }
        }

        switch(shelf){
            case 1:
                if(getShelfQuantity(shelf)+quantity > 1){
                    quantity -= 1-getShelfQuantity(shelf);
                    firstQuantity = 1;
                    virtualView.updateWarehouseDepot(1, firstShelf, firstQuantity);
                    return quantity;
                }else{
                    firstQuantity = getShelfQuantity(shelf)+quantity;
                    virtualView.updateWarehouseDepot(1, firstShelf, firstQuantity);
                    return 0;
                }
            case 2:
                if(getShelfQuantity(shelf)+quantity > 2){
                    quantity -= 2-getShelfQuantity(shelf);
                    secondQuantity = 2;
                    virtualView.updateWarehouseDepot(2, secondShelf, secondQuantity);
                    return quantity;
                }else{
                    secondQuantity = getShelfQuantity(shelf)+quantity;
                    virtualView.updateWarehouseDepot(2, secondShelf, secondQuantity);
                    return 0;
                }
            case 3:
                if(getShelfQuantity(shelf)+quantity > 3){
                    quantity -= 3-getShelfQuantity(shelf);
                    thirdQuantity = 3;
                    virtualView.updateWarehouseDepot(3, thirdShelf, thirdQuantity);
                    return quantity;
                }else{
                    thirdQuantity = getShelfQuantity(shelf)+quantity;
                    virtualView.updateWarehouseDepot(3, thirdShelf, thirdQuantity);
                    return 0;
                }
        }

        return 0;
    }

    /**
     * Remove resources from a shelf
     * @param resource: resource type
     * @param quantity number of resources to be removed, of the specified type
     * @return the number of resources that you need to remove from the Strongbox
     */
    public int removeResource(Resource resource, int quantity) {
        if(quantity < 0) return 0;
        int shelf = 1;
        try {
            if (getShelfResource(1).equals(resource) && getShelfQuantity(1) != 0)
                shelf = 1;
            else if (getShelfResource(2).equals(resource) && getShelfQuantity(2) != 0)
                shelf = 2;
            else if (getShelfResource(3).equals(resource) && getShelfQuantity(3) != 0)
                shelf = 3;
            else return quantity;   //resource not existing on shelves
        }catch(InvalidShelfPosition e){
            System.out.println("Something went wrong");
        }
        switch (shelf){
            case 1:
                if(firstQuantity >= quantity){
                    firstQuantity -= quantity;
                    virtualView.updateWarehouseDepot(1, firstShelf, firstQuantity);
                    return 0;
                }else{
                    quantity -= firstQuantity;
                    firstQuantity = 0;
                    virtualView.updateWarehouseDepot(1, firstShelf, firstQuantity);
                    return quantity;
                }
            case 2:
                if(secondQuantity >= quantity){
                    secondQuantity -= quantity;
                    virtualView.updateWarehouseDepot(2, secondShelf, secondQuantity);
                    return 0;
                }else{
                    quantity -= secondQuantity;
                    secondQuantity = 0;
                    virtualView.updateWarehouseDepot(2, secondShelf, secondQuantity);
                    return quantity;
                }
            case 3:
                if(thirdQuantity >= quantity){
                    thirdQuantity -= quantity;
                    virtualView.updateWarehouseDepot(3, thirdShelf, thirdQuantity);
                    return 0;
                }else{
                    quantity -= thirdQuantity;
                    thirdQuantity = 0;
                    virtualView.updateWarehouseDepot(3, thirdShelf, thirdQuantity);
                    return quantity;
                }
        }
        return 0;
    }

    /**
     * @return the list of extra slots activated
     */
    public ArrayList<ExtraSlot> getExtraSlotList(){
        return new ArrayList<>(extraSlotList);
    }

    /**
     * Remove a resource from the extra slot
     * @param resource type of resources that have to be removed from a extra slot
     * @param quantity number of resources that have to be removed from a extra slot
     * @throws MissingExtraSlot if there isn't an extra slot for the requested resource
     */
    public int removeExtraResource(Resource resource, int quantity) throws MissingExtraSlot {
        for (ExtraSlot slot: extraSlotList)
            if(slot.getResource().equals(resource)) {
                return slot.removeResource(quantity);
            }
        throw new MissingExtraSlot();
    }

    /**
     * Add a resource to the extra slot
     * @param resource type of resources that have to be added inside an extra slot
     * @param quantity number of resources that have to be added from a extra slot
     * @throws MissingExtraSlot if there isn't an extra slot for the requested resource
     */
    public int addExtraResource(Resource resource, int quantity) throws MissingExtraSlot{
        for (ExtraSlot slot: extraSlotList)
            if(slot.getResource().equals(resource)) return slot.addResource(quantity);
        throw new MissingExtraSlot();
    }

    /**
     * Get the quantity of resource stored in the extra slot
     * @param resource extra slot type of resource required
     * @return 0 if there isn't an extra slot for the requested resource
     */
    public int getExtraQuantity(Resource resource){
        for(ExtraSlot slot: extraSlotList)
            if(slot.getResource().equals(resource)) return slot.getQuantity();
        return 0;
    }

    /**
     * create a new ExtraSlot object and initialize it with the resource type
     * @param resourceExtra: resource that can be stored in the extra slot
     */
    public void addExtraSlot(Resource resourceExtra, int ID){
        ExtraSlot slot = new ExtraSlot(resourceExtra, ID);
        extraSlotList.add(slot);
        slot.subscribe(virtualView);
    }

    /**
     *
     * @param resource type of resources required
     * @return quantity of the selected resource in stock, 0 if there are no resource in stock
     */
    public int getResourceQuantity(Resource resource){
        try {
            if (getShelfResource(1).equals(resource)) return getShelfQuantity(1);
            else if (getShelfResource(2).equals(resource)) return getShelfQuantity(2);
            else if (getShelfResource(3).equals(resource)) return getShelfQuantity(3);
        }catch(InvalidShelfPosition e){
            System.out.println("Something went wrong");
        }
        return 0;
    }

    /**
     * It moves a specified quantity of resources from a specified a shelf to an extra slot.
     * @param shelf the specified shelf from which resources are supposed to be moved: the first shelf starts with 1
     * @param quantity quantity of resources to be moved : if quantity is higher than the actual quantity on the shelf, it will be considered to move all the resources that are on the shelf.
     * @throws InvalidShelfPosition if shelf is less than 1 or shelf is greater 3
     * @throws NotEnoughSpaceException if there is not enough space to store all the required resources into the extra slot
     * @throws MissingExtraSlot if does not exist an extra slot that can store the specified resource
     */
    public void moveFromShelfToSlot(int shelf, int quantity) throws InvalidShelfPosition,  NotEnoughSpaceException, MissingExtraSlot{
        if(shelf < 1 || shelf > 3) throw new InvalidShelfPosition();
        if(quantity <= 0) return;
        switch (shelf){
            case 1:
                if(firstQuantity == 0) throw new InvalidShelfPosition();
                break;
            case 2:
                if(secondQuantity == 0) throw new InvalidShelfPosition();
                break;
            case 3:
                if(thirdQuantity == 0) throw new InvalidShelfPosition();
                break;
        }
        ExtraSlot slot = null;
        for(ExtraSlot s:extraSlotList)
            if (getShelfResource(shelf).equals(s.getResource())){
                slot = s;
                break;
            }
        if(slot == null) throw new MissingExtraSlot();
        if(quantity > getShelfQuantity(shelf)) quantity = getShelfQuantity(shelf);
        boolean a = false;
        if(2-slot.getQuantity() < quantity) {
            quantity = 2-slot.getQuantity();
            a = true;
        }
        slot.addResource(quantity);
        switch(shelf){
            case 1:
                firstQuantity -= quantity;
                virtualView.updateWarehouseDepot(1, firstShelf, firstQuantity);
                break;
            case 2:
                secondQuantity -= quantity;
                virtualView.updateWarehouseDepot(2, secondShelf, secondQuantity);
                break;
            case 3:
                thirdQuantity -= quantity;
                virtualView.updateWarehouseDepot(3, thirdShelf, thirdQuantity);
                break;
        }
        if(a) throw new NotEnoughSpaceException();
    }

    /**
     * It moves a specified quantity of resources from an extra slot to a specified shelf.
     * @param resource the specified type of resources that have to be moved from an extra slot
     * @param quantity quantity of resources to be moved : if quantity is higher than the actual quantity in the extra slot, it will be considered to move all the resources that are in the extra slot.
     * @param shelf the specified shelf where the resources are supposed to be moved: the first shelf starts with 1
     * @throws InvalidShelfPosition if shelf is less than 1 or shelf is greater than 3
     * @throws ExistingResourceException if there are already a few resources inside of the specified shelf which type is different from the required one
     * @throws InvalidResourceException if there is no extra slot that stores the type of resources required
     * @throws NotEnoughSpaceException  if there is not enough space to store all the required resources into the shelf
     */
    public void moveFromSlotToShelf(Resource resource, int quantity, int shelf) throws InvalidShelfPosition, ExistingResourceException, InvalidResourceException, NotEnoughSpaceException {
        if(shelf < 1 || shelf > 3) throw new InvalidShelfPosition();
        ExtraSlot slot = null;
        for(ExtraSlot s : extraSlotList)
            if(s.getResource().equals(resource)) slot = s;
        if(slot == null) throw new InvalidResourceException();
        if(quantity <= 0) return;
        if(quantity > slot.getQuantity()) quantity = slot.getQuantity();
        boolean a = false;
        switch (shelf){
            case 1:
                if(firstQuantity != 0 && !resource.equals(firstShelf)) throw new ExistingResourceException();
                if(1-firstQuantity < quantity){
                    a = true;
                    quantity = 1-firstQuantity;
                }
                firstQuantity += quantity;
                slot.removeResource(quantity);
                firstShelf = resource;
                virtualView.updateWarehouseDepot(1, firstShelf, firstQuantity);
                break;
            case 2:
                if(secondQuantity != 0 && !resource.equals(secondShelf)) throw new ExistingResourceException();
                if(2-secondQuantity < quantity){
                    a = true;
                    quantity = 2-secondQuantity;
                }
                secondQuantity += quantity;
                slot.removeResource(quantity);
                secondShelf = resource;
                virtualView.updateWarehouseDepot(2, secondShelf, secondQuantity);
                break;
            case 3:
                if(thirdQuantity != 0 && !resource.equals(thirdShelf)) throw new ExistingResourceException();
                if(3-thirdQuantity < quantity){
                    a = true;
                    quantity = 3-thirdQuantity;
                }
                thirdQuantity += quantity;
                slot.removeResource(quantity);
                thirdShelf = resource;
                virtualView.updateWarehouseDepot(3, thirdShelf, thirdQuantity);
                break;
        }
        if(a) throw new NotEnoughSpaceException();
    }

    public void subscribe(VirtualView virtualView){
        this.virtualView = virtualView;
    }

    /**
     * returns the amount in the shelves and the slots.
     * @return the amount in the shelves and the slots.
     */
    public int getAmountOfResources(){
        int amount = 0;
        amount += firstQuantity;
        amount += secondQuantity;
        amount += thirdQuantity;
        for(ExtraSlot slot : extraSlotList){
         amount += slot.getQuantity();
        }
        return amount;
    }
}
