package it.polimi.ingsw.model;

import it.polimi.ingsw.model.benefit.Resource;
import it.polimi.ingsw.model.exceptions.*;

import java.util.HashMap;
import java.util.Map;

public class Strongbox {
    private Map<Resource, Integer> content;
    private Map<Resource, Integer> lastProduced;

    public Strongbox(){
        content = new HashMap<>();
        lastProduced = new HashMap<>();
    }

    /**
     *
     * @param resource
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
     * @param resource
     * @param quantity
     * @throws NegativeQuantityException
     */
    public void addResource(Resource resource, int quantity) throws NegativeQuantityException{
        if(resource == null) return;
        if(quantity < 0) throw new NegativeQuantityException();
        if(lastProduced.containsKey(resource)) lastProduced.put(resource, lastProduced.get(resource)+quantity);
        else lastProduced.put(resource, quantity);
    }

    /**
     *
     * @param resource
     * @param quantity
     * @throws NegativeQuantityException
     */
    public void removeResource(Resource resource, int quantity) throws NegativeQuantityException{
        if(resource == null) return;
        if(quantity < 0) throw new NegativeQuantityException();
        if(content.get(resource) == quantity) content.remove(resource);
        else content.put(resource, content.get(resource)-quantity);
    }

    /**
     * Finalize previous productions, adding the resources in the strongbox
     */
    public void addProduced(){
        for(Map.Entry<Resource, Integer> couple :lastProduced.entrySet())
            content.put(couple.getKey(), couple.getValue());
        lastProduced.clear();
    }

}
