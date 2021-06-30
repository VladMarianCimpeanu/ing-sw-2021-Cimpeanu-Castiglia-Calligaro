package it.polimi.ingsw.client.modelLight;

import it.polimi.ingsw.client.Color;

import java.util.ArrayList;

/**
 * This class gives access to all the cards that can be purchased at the moment, in order to display them.
 * Development cards are displayed in a grid [3 x 4]
 */
public abstract class DevelopmentCardSetView {
    protected ArrayList<ArrayList<Integer>> decks;

    /**
     * Update the set of cards: it replaces the card with the specified color and level with a new one.
     * @param color specified color of the card.
     * @param level specified level of the card
     * @param ID ID of the new card. If ID is 0, than the deck becomes empty.
     */
    public abstract void updateSet(Color color, int level, int ID);

    /**
     * This method gives the ID of the card placed on the top of the specified deck.
     * @param color development card deck color.
     * @param level development card deck level.
     * @return ID of the development card at the specified place.
     */
    public int getCard(Color color, int level){
        return decks.get(level - 1).get(color.getIndex());
    }

    /**
     * This method set the development cards available to be purchased.
     * @param decks specified decks to be set as new cards available.
     */
    public abstract void setDecks(ArrayList<ArrayList<Integer>> decks);

    /**
     * It shows the development card available to buy.
     */
    public abstract void show();

    public void update(){}
}
