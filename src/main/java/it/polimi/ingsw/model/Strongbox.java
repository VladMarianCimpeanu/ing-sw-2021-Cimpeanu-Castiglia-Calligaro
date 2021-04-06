package it.polimi.ingsw.model;

import it.polimi.ingsw.model.benefit.Resource;
import it.polimi.ingsw.model.exceptions.*;

import java.util.Map;

public class Strongbox {
    private Map<Resource, Integer> content;
    private Map<Resource, Integer> lastProduced;

    public Strongbox(){

    }

    /**
     *
     * @param resource
     * @return quantity of selected resources in stock
     */
    public int getResourceQuantity(Resource resource){
    return 0;
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

    }

    /**
     *
     * @param resource
     * @param quantity
     * @throws NegativeQuantityException
     */
    public void removeResource(Resource resource, int quantity) throws NegativeQuantityException{

    }

    /**
     * Finalize previous productions, adding the resources in the strongbox
     */
    public void addProduced(){

    }

}
