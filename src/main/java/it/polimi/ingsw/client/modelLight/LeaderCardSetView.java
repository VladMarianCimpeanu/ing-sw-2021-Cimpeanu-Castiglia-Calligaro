package it.polimi.ingsw.client.modelLight;

import it.polimi.ingsw.client.Resource;

import java.util.ArrayList;

/**
 * This class gives access to all the leader cards owned by the player, in order to display it.
 */
public abstract class LeaderCardSetView {
    protected ArrayList<Integer> playerCards;

    public abstract void show();
    public abstract void activate(int id);
    public abstract void dumpMessage(String message);

    public void update(ArrayList<Integer> idS){
        for(int id: idS){
            if(!playerCards.contains(id)) playerCards.add(id);
        }
    }

    public int getIDfromIndex(int index){
        return playerCards.get(index-1);
    }

    public void keep(ArrayList<Integer> idS) {
        playerCards = new ArrayList<>(idS);
    }

    public void remove(int id) {
        playerCards.remove((Object) id);
    }

    public abstract void updateExtraSlot(int id, Resource resource, int quantity);
}