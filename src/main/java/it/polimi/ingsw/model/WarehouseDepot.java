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
    //Useless attributes?
    private final int firstMax = 1;
    private final int secondMax = 2;
    private final int thirdMax = 3 ;
    private ArrayList<ExtraSlot> extraSlotList;

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

    /**
     * Move resources from a shelf to another shelf
     * @param fromShelf
     * @param toShelf
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
                    break;
                case 3:
                    thirdQuantity = getShelfQuantity(min);
                    thirdShelf = getShelfResource(min);
                    break;
            }
            switch (min){
                case 1:
                    firstQuantity = temp_quantity;
                    firstShelf = temp_resource;
                    break;
                case 2:
                    secondQuantity = temp_quantity;
                    secondShelf = temp_resource;
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
                    return quantity;
                }else{
                    firstQuantity = getShelfQuantity(shelf)+quantity;
                    return 0;
                }
            case 2:
                if(getShelfQuantity(shelf)+quantity > 2){
                    quantity -= 2-getShelfQuantity(shelf);
                    secondQuantity = 2;
                    return quantity;
                }else{
                    secondQuantity = getShelfQuantity(shelf)+quantity;
                    return 0;
                }
            case 3:
                if(getShelfQuantity(shelf)+quantity > 3){
                    quantity -= 3-getShelfQuantity(shelf);
                    thirdQuantity = 3;
                    return quantity;
                }else{
                    thirdQuantity = getShelfQuantity(shelf)+quantity;
                    return 0;
                }
        }
        return 0;
    }

    /**
     * Remove resources from a shelf
     * @param resource: resource type
     * @param quantity
     * @return the number of resources that you need to remove from the Strongbox
     */
    public int removeResource(Resource resource, int quantity) throws InvalidShelfPosition {
        if(quantity < 0) return 0;
        int shelf;
        if(getShelfResource(1).equals(resource) && getShelfQuantity(1) != 0)
            shelf = 1;
        else if(getShelfResource(2).equals(resource) && getShelfQuantity(2) != 0)
            shelf = 2;
        else if(getShelfResource(3).equals(resource) && getShelfQuantity(3) != 0)
            shelf = 3;
        else return quantity;   //resource not existing on shelves
        switch (shelf){
            case 1:
                if(firstQuantity >= quantity){
                    firstQuantity -= quantity;
                    return 0;
                }else{
                    quantity -= firstQuantity;
                    firstQuantity = 0;
                    return quantity;
                }
            case 2:
                if(secondQuantity >= quantity){
                    secondQuantity -= quantity;
                    return 0;
                }else{
                    quantity -= secondQuantity;
                    secondQuantity = 0;
                    return quantity;
                }
            case 3:
                if(thirdQuantity >= quantity){
                    thirdQuantity -= quantity;
                    return 0;
                }else{
                    quantity -= thirdQuantity;
                    thirdQuantity = 0;
                    return quantity;
                }
        }
        return 0;
    }

    /**
     *
     * @return
     */
    public ArrayList<ExtraSlot> getExtraSlotList(){
        return new ArrayList<>(extraSlotList);
    }

    /**
     * Remove a resource from the extra slot
     * @param resource
     * @param quantity
     * @throws MissingExtraSlot if there isn't an extra slot for the requested resource
     */
    private int removeExtraResource(Resource resource, int quantity) throws MissingExtraSlot {
        for (ExtraSlot slot: extraSlotList)
            if(slot.getResource().equals(resource)) return slot.removeResource(quantity);
        throw new MissingExtraSlot();
    }

    /**
     * Add a resource to the extra slot
     * @param resource
     * @param quantity
     * @throws MissingExtraSlot if there isn't an extra slot for the requested resource
     */
    public int addExtraResource(Resource resource, int quantity) throws MissingExtraSlot{
        for (ExtraSlot slot: extraSlotList)
            if(slot.getResource().equals(resource)) return slot.addResource(quantity);
        throw new MissingExtraSlot();
    }

    /**
     * Get the quantity of resource stored in the extra slot
     * @param resource
     * @return 0 if there isn't an extra slot for the requested resource
     */
    private int getExtraQuantity(Resource resource){
        for(ExtraSlot slot: extraSlotList)
            if(slot.getResource().equals(resource)) return slot.getQuantity();
        return 0;
    }

    /**
     * create a new ExtraSlot object and initialize it with the resource type
     * @param resourceExtra: resource that can be stored in the extra slot
     */
    public void addExtraSlot(Resource resourceExtra){
        extraSlotList.add(new ExtraSlot(resourceExtra));
    }

    /**
     *
     * @param resource
     * @return quantity of the selected resource in stock, 0 if there are no resource in stock
     */
    public int getResourceQuantity(Resource resource) throws InvalidShelfPosition {
        if(getShelfResource(1).equals(resource)) return getShelfQuantity(1);
        else if(getShelfResource(2).equals(resource)) return getShelfQuantity(2);
        else if(getShelfResource(3).equals(resource)) return getShelfQuantity(3);
        return 0;
    }

    /**
     * It moves a specified quantity of resources from a specified a shelf to an extra slot.
     * @param shelf the specified shelf from which resources are supposed to be moved: the first shelf starts with 1
     * @param quantity quantity of resources to be moved : if quantity is higher than the actual quantity on the shelf, it will be considered to move all the resources that are on the shelf.
     * @throws InvalidShelfPosition
     * @throws NotEnoughSpaceException
     * @throws MissingExtraSlot
     */
    public void moveFromShelfToSlot(int shelf, int quantity) throws InvalidShelfPosition,  NotEnoughSpaceException, MissingExtraSlot{
        if(shelf < 1 || shelf > 3) throw new InvalidShelfPosition();
        if(quantity <= 0) return;
        switch (shelf){
            case 1:
                if(firstQuantity == 0) throw new InvalidShelfPosition();
            case 2:
                if(secondQuantity == 0) throw new InvalidShelfPosition();
            case 3:
                if(thirdQuantity == 0) throw new InvalidShelfPosition();
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
                break;
            case 2:
                secondQuantity -= quantity;
                break;
            case 3:
                thirdQuantity -= quantity;
                break;
        }
        if(a) throw new NotEnoughSpaceException();
    }

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
                break;
            case 2:
                if(secondQuantity != 0 && !resource.equals(secondShelf)) throw new ExistingResourceException();
                if(2-secondQuantity < quantity){
                    a = true;
                    quantity = 2-secondQuantity;
                }
                secondQuantity += quantity;
                slot.removeResource(quantity);
                break;
            case 3:
                if(thirdQuantity != 0 && !resource.equals(thirdShelf)) throw new ExistingResourceException();
                if(3-thirdQuantity < quantity){
                    a = true;
                    quantity = 3-thirdQuantity;
                }
                thirdQuantity += quantity;
                slot.removeResource(quantity);
                break;
        }
        if(a) throw new NotEnoughSpaceException();
    }
}
