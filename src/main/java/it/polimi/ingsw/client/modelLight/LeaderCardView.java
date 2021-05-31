package it.polimi.ingsw.client.modelLight;

import it.polimi.ingsw.client.Resource;

import java.util.ArrayList;

public abstract class LeaderCardView {
    protected ArrayList<Resource> extraSlot = new ArrayList<>();

    public abstract void show();
    public abstract void activate();
    public abstract int getID();
    public void updateExtraSlot(Resource resource, int quantity){
        extraSlot = new ArrayList<>();
        for(int i = 0; i < quantity; i++)
            extraSlot.add(resource);
    }
}
