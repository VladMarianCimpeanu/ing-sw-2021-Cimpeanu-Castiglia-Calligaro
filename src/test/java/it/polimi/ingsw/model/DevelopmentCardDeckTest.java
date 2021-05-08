package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.InvalidReadException;
import it.polimi.ingsw.model.exceptions.NoCardException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DevelopmentCardDeckTest {

    @Test
    @DisplayName("Checking building dev card set")
    void getCards() throws InvalidReadException, IOException {
        ArrayList<DevelopmentCard> cards = DevelopmentCardDeck.getDevelopmentCardDeck();
        assertEquals(48, cards.size());
    }

    @Test
    @DisplayName("Checking correct IDs")
    void getCard() throws InvalidReadException, IOException, NoCardException {
        assertEquals(1, DevelopmentCardDeck.getDevelopmentCard(1).getID());
        assertEquals(2, DevelopmentCardDeck.getDevelopmentCard(2).getID());
        assertEquals(10, DevelopmentCardDeck.getDevelopmentCard(10).getID());
        assertEquals(48, DevelopmentCardDeck.getDevelopmentCard(48).getID());
        assertEquals(5, DevelopmentCardDeck.getDevelopmentCard(5).getID());
        assertEquals(12, DevelopmentCardDeck.getDevelopmentCard(12).getID());
        assertEquals(18, DevelopmentCardDeck.getDevelopmentCard(18).getID());
        assertEquals(8, DevelopmentCardDeck.getDevelopmentCard(8).getID());
    }

    @Test
    @DisplayName("Checking incorrect developmentCardIDs")
    void incorrectDevId() {
        assertThrows(NoCardException.class, () ->
                DevelopmentCardDeck.getDevelopmentCard(0));
        assertThrows(NoCardException.class, () ->
                DevelopmentCardDeck.getDevelopmentCard(49));
    }
}