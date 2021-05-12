package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.benefit.Benefit;
import it.polimi.ingsw.server.model.benefit.Faith;
import it.polimi.ingsw.server.model.benefit.Resource;

import java.util.HashMap;
import java.util.Map;

/**
 * An extra production produce a faith point anda resource at will. Extra production requires only a specific resource to
 * be performed.
 */
public class ExtraProduction implements Production{
    private final Resource resourceIn;
    private int ID;
    /**
     * @param resourceIn input resource for extra production
     */
    public ExtraProduction(Resource resourceIn, int ID){
        this.resourceIn = resourceIn;
        this.ID = ID;
    }

    /**
     *This method return which resource extra production requires to be activate.
     * @return input resource for extra production
     */
    public Resource getResourceIn(){
        return resourceIn;
    }

    /**
     * @param resourceOut specified resource to be produced
     * @return a Map containing one faith point and the selected resource
     */
    public Map<Benefit,Integer> getResourceOut(Resource resourceOut){
        Map<Benefit, Integer> resourcesProduced = new HashMap<>();
        resourcesProduced.put(Faith.giveFaith(), 1);
        resourcesProduced.put(resourceOut, 1);
        return resourcesProduced;
    }

    public int getID() {
        return ID;
    }
}
