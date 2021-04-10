package it.polimi.ingsw.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;
import it.polimi.ingsw.model.exceptions.*;

import static java.util.Collections.shuffle;

public class DevelopmentCardSet {
    private ArrayList<ArrayList<Stack<DevelopmentCard>>> availableDevelopmentCards;

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

    public ArrayList<ArrayList<Stack<DevelopmentCard>>> getAvailableDevelopmentCards() {
        //TO DO

        return availableDevelopmentCards;
    }

    /**
     * Select a deck by color and level, and check what's the card on top
     * @param color color of the cards belonging to the deck
     * @param level of the cards belonging to the deck
     * @throws WrongLevelException if level isn't between 1 and 3
     * @return
     */
    public DevelopmentCard peekCard(Color color, int level) throws WrongLevelException, NoCardException {
        if (level < 1 || level > 3) throw new WrongLevelException();
        if (availableDevelopmentCards.get(level - 1).get(color.getIndex()).isEmpty()) throw new NoCardException();
        return availableDevelopmentCards.get(level - 1).get(color.getIndex()).lastElement();
    }

    /**
     * Select a deck by color and level, and pick the card on top
     * @param color color of the cards belonging to the deck
     * @param level of the cards belonging to the deck
     * @throws WrongLevelException if level isn't between 1 and 3
     * @throws NoCardException if the selected deck is empty
     * @return
     */
    public DevelopmentCard drawCard(Color color, int level) throws WrongLevelException, NoCardException {
        if (level < 1 || level > 3) throw new WrongLevelException();
        if (availableDevelopmentCards.get(level - 1).get(color.getIndex()).isEmpty()) throw new NoCardException();
        return availableDevelopmentCards.get(level - 1).get(color.getIndex()).pop();
    }

    /**
     * @param color of the cards belonging to the deck
     * @param level of the cards belonging to the deck
     * @return the number of card inside to the specified deck
     * @throws WrongLevelException if level < 1 or level > 3
     */
    public int getAvailableQuantity(Color color, int level) throws WrongLevelException{
        if (level < 1 || level > 3) throw new WrongLevelException();
        return availableDevelopmentCards.get(level - 1).get(color.getIndex()).size();
    }
}
