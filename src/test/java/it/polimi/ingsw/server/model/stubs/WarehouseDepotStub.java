package it.polimi.ingsw.server.model.stubs;

import it.polimi.ingsw.server.model.WarehouseDepot;
import it.polimi.ingsw.server.model.benefit.Resource;

import java.util.HashMap;
import java.util.Map;

public class WarehouseDepotStub extends WarehouseDepot {
    public Map<Resource, Integer> content;

    public WarehouseDepotStub(){
        content = new HashMap<Resource, Integer>();
        content.put(Resource.COIN,0);
        content.put(Resource.SERVANT,0);
        content.put(Resource.SHIELD,0);
        content.put(Resource.STONE,0);
        subscribe(new VirtualViewStub());
    }

    @Override
    public int getResourceQuantity(Resource resource){
        return content.get(resource);
    }

    @Override
    public int removeResource(Resource resource, int quantity) {
        int prev = content.get(resource);
        int curr, ret;
        if(prev <= quantity){
            curr = 0;
            ret = quantity - prev;
        }else{
            curr = prev - quantity;
            ret = 0;
        }
        content.replace(resource, curr);
        return ret;
    }

}
