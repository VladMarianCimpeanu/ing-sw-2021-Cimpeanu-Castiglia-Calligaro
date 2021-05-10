package it.polimi.ingsw.model.stubs;

import it.polimi.ingsw.model.Strongbox;
import it.polimi.ingsw.model.benefit.Resource;

import java.util.HashMap;
import java.util.Map;

public class StrongboxStub extends Strongbox {
    public Map<Resource, Integer> content;

    public StrongboxStub(){
        content = new HashMap<>();
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
        int curr;
        if(prev <= quantity){
            curr = 0;
            content.replace(resource, curr);
            return quantity - prev;
        }else{
            curr = prev - quantity;
            content.replace(resource, curr);
            return 0;
        }
    }

}
