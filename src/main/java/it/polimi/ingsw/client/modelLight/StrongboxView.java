package it.polimi.ingsw.client.modelLight;

import it.polimi.ingsw.client.Resource;

import java.util.HashMap;
import java.util.Map;

public abstract class StrongboxView {
    protected Map<Resource, Integer> content;
    public StrongboxView(){
        content = new HashMap<>();
        for(Resource resource: Resource.values())
            if(resource != Resource.FAITH)
                content.put(resource, 0);
    }
    public abstract void show();
    public void update(Map<Resource, Integer> resources){
        content = resources;
    }

}
