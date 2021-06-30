package it.polimi.ingsw.client.modelLight;

import it.polimi.ingsw.client.Resource;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * This class has all the information about a player's strongbox client side.
 */
public abstract class StrongboxView {
    protected Map<Resource, Integer> content;
    public StrongboxView(){
        content = new TreeMap<>();
        for(Resource resource: Resource.values())
            if(resource != Resource.FAITH)
                content.put(resource, 0);
    }
    public abstract void show();
    public void update(Map<Resource, Integer> resources){
        content = resources;
        show();
    }
    public int getQuantity(Resource resource){
        if(!content.containsKey(resource)) return 0;
        return content.get(resource);
    }

}
