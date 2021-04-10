package it.polimi.ingsw.model;

import it.polimi.ingsw.model.benefit.Benefit;
import it.polimi.ingsw.model.benefit.Faith;
import it.polimi.ingsw.model.benefit.Resource;

import java.util.HashMap;
import java.util.Map;

public class ExtraProduction {
    private Resource resourceIn;

    /**
     *
     * @param resourceIn input resource for extra production
     */
    public ExtraProduction(Resource resourceIn){
        this.resourceIn = resourceIn;
    }

    /**
     *
     * @return input resource for extra production
     */
    public Resource getResourceIn(){
        return resourceIn;
    }

    /**
     *
     * @param resourceOut specified resource to be produced
     * @return a Map containing one faith point and the selected resource
     */
    public Map<Benefit,Integer> getResourceOut(Resource resourceOut){
        Map<Benefit, Integer> resourcesProduced = new HashMap<>();
        resourcesProduced.put(Faith.giveFaith(), 1);
        resourcesProduced.put(resourceOut, 1);
        return resourcesProduced;
    }
}
