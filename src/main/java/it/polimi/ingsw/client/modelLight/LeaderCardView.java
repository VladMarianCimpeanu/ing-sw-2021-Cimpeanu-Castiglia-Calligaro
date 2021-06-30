package it.polimi.ingsw.client.modelLight;

import it.polimi.ingsw.client.Resource;

import java.util.ArrayList;

/**
 * This class gives access to all the information of a specific leader card, in order to display it.
 */
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
