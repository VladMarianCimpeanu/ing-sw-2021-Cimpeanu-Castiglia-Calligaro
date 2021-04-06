package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.NoCardException;
import it.polimi.ingsw.model.exceptions.WrongLevelException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.NoSuchFileException;

import static it.polimi.ingsw.model.Color.YELLOW;
import static org.junit.jupiter.api.Assertions.*;

class DevelopmentCardSetTest {

    /**
     * peek a card on top of one deck and check(looking at the returned card) if level and color match the request done
     * it also checks if the objects returned in 2 requests, using the same input each time, are equal
     * @throws WrongLevelException trying to use wrong levels
     */
    @Test
    void peekCard() throws WrongLevelException, NoSuchFileException {
        DevelopmentCardSet set = new DevelopmentCardSet();
        DevelopmentCard cardOnTop = set.peekCard(YELLOW, 1);
        assertTrue(cardOnTop.getColor() == YELLOW);
        assertTrue(cardOnTop.getLevel() == 1);
        assertTrue(cardOnTop == set.peekCard(YELLOW, 1));
        assertThrows(WrongLevelException.class, () -> {
            set.peekCard(YELLOW, 4);
        });
        assertThrows(WrongLevelException.class, () -> {
            set.peekCard(YELLOW, -1);
        });
    }

    /**
     * draw a card from an untouched deck and check(looking at the returned card) if level and color match the request done
     * then it draws other 3 cards from the same deck used before in order to expect the deck to be empty
     * @throws WrongLevelException trying to use wrong levels
     * @throws NoCardException trying to draw a card from an empty deck
     */

    @Test
    void drawCard() throws WrongLevelException, NoCardException, NoSuchFileException {
        DevelopmentCardSet set = new DevelopmentCardSet();
        DevelopmentCard cardOnTop = set.drawCard(YELLOW, 1);
        assertTrue(cardOnTop.getColor() == YELLOW);
        assertTrue(cardOnTop.getLevel() == 1);
        assertTrue(cardOnTop != set.peekCard(YELLOW, 1));
        for(int i = 0; i<3; i++)
            set.drawCard(YELLOW, 1);
        assertThrows(NoCardException.class, () -> {
            set.drawCard(YELLOW, 1);
        });
        assertThrows(WrongLevelException.class, () -> {
            set.drawCard(YELLOW, 4);
        });
        assertThrows(WrongLevelException.class, () -> {
            set.drawCard(YELLOW, -1);
        });
    }

    @Test
    @DisplayName("Illegal levels")
    void getAvailableQuantityException(){
        DevelopmentCardSet set = new DevelopmentCardSet();
        assertThrows(WrongLevelException.class, () -> {
            set.getAvailableQuantity(YELLOW, -1);
        });
        assertThrows(WrongLevelException.class, () -> {
            set.getAvailableQuantity(YELLOW, 0);
        });
        assertThrows(WrongLevelException.class, () -> {
            set.getAvailableQuantity(YELLOW, 4);
        });
        assertThrows(WrongLevelException.class, () -> {
            set.getAvailableQuantity(YELLOW, 5);
        });
    }

}