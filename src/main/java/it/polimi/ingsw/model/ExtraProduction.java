package it.polimi.ingsw.model;

import it.polimi.ingsw.model.benefit.Benefit;
import it.polimi.ingsw.model.benefit.Resource;

import java.util.Map;

public class ExtraProduction {
    private Resource resourceIn;

    /**
     *
     * @param resourceIn input resource for extra production
     */
    public ExtraProduction(Resource resourceIn){

    }

    /**
     *
     * @return input resource for extra production
     */
    public Resource getResourceIn(){
        return null;
    }

    /**
     *
     * @param resourceOut
     * @return a Map containing one faith point and the selected resource
     */
    public Map<Benefit,Integer> getResourceOut(Resource resourceOut){
        return null;
    }
}
