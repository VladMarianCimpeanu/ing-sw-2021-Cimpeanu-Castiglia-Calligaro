package it.polimi.ingsw.client.modelLight;

import java.util.ArrayList;

/**
 * This class gives the access to all development cards owned by a specific player, in order to display them.
 */
public abstract class DevelopmentCardDecksView {
    protected ArrayList<Integer> playerCards;

    public DevelopmentCardDecksView(){
        playerCards = new ArrayList<>();
        for(int index = 0; index < 3; index ++) playerCards.add(0);
    }

    /**
     * This method adds a new development card to the set of cards owned by the player.
     * If the card is placed on a deck that already contains a card, it replaces it with the new one.
     * @param deckIndex specified index of the deck to place the card
     * @param ID ID of development card to place.
     * @param nickname nickname of the player that bought this card.
     */
    public abstract void addCard(int deckIndex, int ID, String nickname);

    /**
     * This method is used to add a development card owned by a specific player, during the reset up phase
     * (if the player crashed before)
     * @param deckIndex specified deck index to place the card
     * @param ID ID of the development card to place. (If the ID is 0, than the deck is considered empty)
     * @param nickname nickname of the owner.
     */
    public void reloadCard(int deckIndex, int ID, String nickname){
        playerCards.set(deckIndex - 1, ID);
    }

    /**
     * This method gives the development cards owned by a specific player.
     * @return a copy of the list containing all the development cards owned by this player.
     */
    public ArrayList<Integer> getCards(){
        return new ArrayList<>(playerCards);
    }

    /**
     * Given a specified development card it returns the deck where it is placed.
     * @param ID ID of the specified development card.
     * @return index of the deck.
     */
    public int getPos(int ID){
        return playerCards.indexOf(ID)+1;
    }

    /**
     * It show the decks owned by this player.
     */
    public abstract void show();

    public void update(){}
}
