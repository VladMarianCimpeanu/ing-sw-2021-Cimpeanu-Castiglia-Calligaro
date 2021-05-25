package it.polimi.ingsw.client.modelLight;

import it.polimi.ingsw.client.Color;

import java.util.ArrayList;

public abstract class DevelopmentCardSetView {
    protected ArrayList<ArrayList<Integer>> decks;

    public abstract void updateSet(Color color, int level, int ID);

    public int getCard(Color color, int level){
        return decks.get(level - 1).get(color.getIndex());
    }

    /**
     * This method set the development cards available to be purchased.
     * @param decks specified decks to be set as new cards available.
     */
    public abstract void setDecks(ArrayList<ArrayList<Integer>> decks);

    public abstract void show();

    public void update(){}
}
