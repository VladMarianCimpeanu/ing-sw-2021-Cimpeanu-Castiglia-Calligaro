package it.polimi.ingsw.client.modelLight;

import it.polimi.ingsw.client.Color;

import java.util.ArrayList;

public abstract class DevelopmentCardSetView {
    protected ArrayList<ArrayList<Integer>> decks;

    public void updateSet(Color color, int level, int ID){
        decks.get(level - 1).set(color.getIndex(), ID);
    }

    public int getCard(Color color, int level){
        return decks.get(level - 1).get(color.getIndex());
    }

    public abstract void show();
    public void update(){}
}
