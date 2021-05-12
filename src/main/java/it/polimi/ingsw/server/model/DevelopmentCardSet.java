package it.polimi.ingsw.server.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

import it.polimi.ingsw.server.controller.VirtualView;
import it.polimi.ingsw.server.model.exceptions.*;

import static java.util.Collections.shuffle;


/**
 * This class represents the set of development cards that can be bought in the current game. The set is formed by 12 decks.
 * and cards are grouped by color and level.
 */
public class DevelopmentCardSet {
    private final ArrayList<ArrayList<Stack<DevelopmentCard>>> availableDevelopmentCards;
    private VirtualView virtualView;
    public DevelopmentCardSet() throws IOException, InvalidReadException {

        // FIRST INDEX == LEVEL ORDER(1, 2, 3)
        // SECOND INDEX == COLOR ORDER(GREEN, BLUE, YELLOW, PURPLE)
        availableDevelopmentCards = new ArrayList<>();
        for (int level = 0; level < 3; level++) {
            availableDevelopmentCards.add(new ArrayList<>());
            for (Color color : Color.values()) availableDevelopmentCards.get(level).add(new Stack<>());
        }
        ArrayList<DevelopmentCard> tempDeck = DevelopmentCardDeck.getDevelopmentCardDeck();
        for (DevelopmentCard card : tempDeck)
            availableDevelopmentCards.get(card.getLevel() - 1).get(card.getColor().getIndex()).add(card);
        for (int level = 0; level < 3; level++) {
            for (Color color : Color.values()) shuffle(availableDevelopmentCards.get(level).get(color.getIndex()));
        }
    }

    /**
     * this method returns all the cards that can be bought.
     * @return a matrix of stacks: the first index orders stacks by level(level 1[0], level2[1], level3[2]), while second index orders stacks by color. Colors are ordered following
     * the indexes imposed by the Color enumeration.
     */
    public ArrayList<ArrayList<Stack<DevelopmentCard>>> getAvailableDevelopmentCards() {
        return new ArrayList<>(availableDevelopmentCards);
    }

    /**
     * Select a deck by color and level, and check what's the card on top. Pay attention the development card selected will not be removed by its own stack.
     * @param color color of the cards belonging to the deck.
     * @param level of the cards belonging to the deck.
     * @throws WrongLevelException if level isn't between 1 and 3.
     * @throws NoCardException if there are no more cards that have the specified color and level.
     * @return the development card which has the color and level specified from the top of its own deck.
     */
    public DevelopmentCard peekCard(Color color, int level) throws WrongLevelException, NoCardException {
        if (level < 1 || level > 3) throw new WrongLevelException();
        if (availableDevelopmentCards.get(level - 1).get(color.getIndex()).isEmpty()) throw new NoCardException();
        return availableDevelopmentCards.get(level - 1).get(color.getIndex()).lastElement();
    }

    /**
     * Select a deck by color and level, and remove the card on top.
     * @param color color of the cards belonging to the deck.
     * @param level of the cards belonging to the deck.
     * @throws WrongLevelException if level isn't between 1 and 3.
     * @throws NoCardException if the selected deck is empty.
     * @return the development card on top of the selected deck.
     */
    public DevelopmentCard drawCard(Color color, int level) throws WrongLevelException, NoCardException {
        if (level < 1 || level > 3) throw new WrongLevelException();
        if (availableDevelopmentCards.get(level - 1).get(color.getIndex()).isEmpty()) throw new NoCardException();
        virtualView.updateDevCardDrawn(color, level, peekCard(color, level).getID());
        return availableDevelopmentCards.get(level - 1).get(color.getIndex()).pop();
    }

    /**
     * @param color of the cards belonging to the deck
     * @param level of the cards belonging to the deck
     * @return the number of cards inside to the specified deck
     * @throws WrongLevelException if level < 1 or level > 3
     */
    public int getAvailableQuantity(Color color, int level) throws WrongLevelException{
        if (level < 1 || level > 3) throw new WrongLevelException();
        return availableDevelopmentCards.get(level - 1).get(color.getIndex()).size();
    }

    public void subscribe(VirtualView virtualView){
        this.virtualView = virtualView;
    }

}
