package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.controller.VirtualView;
import it.polimi.ingsw.server.model.benefit.Resource;
import it.polimi.ingsw.server.model.exceptions.*;

import java.util.HashMap;
import java.util.Map;
/**
 * Identify Player's Strongbox, composed of unlimited space for storing resources
 * Each player has the reference to a unique and personal Strongbox
 * Here can be store resources coming from productions
 * The resources store here can be used to buy development cards, activate productions and activate leader cards
 */

public class Strongbox {
    private final Map<Resource, Integer> content;
    private final Map<Resource, Integer> lastProduced;
    private VirtualView virtualView;

    public Strongbox(){
        content = new HashMap<>();
        lastProduced = new HashMap<>();
    }

    /**
     * @param resource type of resource required
     * @return quantity of selected resources in stock
     */
    public int getResourceQuantity(Resource resource){
        if(!content.containsKey(resource)) return 0;
        return content.get(resource);
    }

    /**
     * Save resources produced by a developmentCard.
     * It doesn't really add the resources in the strongbox,
     * to do that you need to call addProduced() method
     * @param resource type of resources required
     * @param quantity number of resources, of the specified type, that have to be added to the Strongbox
     * @throws NegativeQuantityException if quantity is negative.
     */
    public void addResource(Resource resource, int quantity) throws NegativeQuantityException{
        if(resource == null) return;
        if(quantity < 0) throw new NegativeQuantityException();
        if(lastProduced.containsKey(resource)) lastProduced.put(resource, lastProduced.get(resource)+quantity);
        else lastProduced.put(resource, quantity);
        virtualView.updateLastProduced(lastProduced);
    }

    /**
     *
     * @param resource type of resources required
     * @param quantity number of resources, of the specified type, that have to be removed from the Strongbox
     * @throws NegativeQuantityException if quantity is less than 0
     */
    public int removeResource(Resource resource, int quantity) throws NegativeQuantityException{
        if(resource == null) throw new NullPointerException();
        if(quantity < 0) throw new NegativeQuantityException();
        if(content.get(resource) <= quantity) {
            int resourceAmount = quantity - content.get(resource);
            content.remove(resource);
            virtualView.updateStrongbox(content);
            return resourceAmount;
        }
        else {
            content.put(resource, content.get(resource)-quantity);
            virtualView.updateStrongbox(content);
            return 0;
        }

    }

    /**
     * Finalize previous productions, adding the resources in the strongbox
     */
    public void addProduced(){
        for(Resource res: lastProduced.keySet()) {
            content.put(res, content.getOrDefault(res, 0) + lastProduced.get(res));
        }
        lastProduced.clear();
        virtualView.updateLastProduced(lastProduced);
        virtualView.updateStrongbox(content);
    }

    public Map<Resource, Integer> getResources() {
        return new HashMap<>(content);
    }

    public void subscribe(VirtualView virtualView){
        this.virtualView = virtualView;
    }

    /**
     * calculate the amount of resources in the strongbox.
     * @return the amount of resources int the strongbox.
     */
    public int getAmountOfResources(){
        int amount = 0;
        for(Integer resAmount : content.values())
            amount += resAmount;
        return amount;
    }
}
