package it.polimi.ingsw;

import it.polimi.ingsw.model.exceptions.NoCardException;
import it.polimi.ingsw.model.leaderCards.LeaderCard;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class JsonToLeaderCardTest {

    @Test
    @DisplayName("Checking correct loading of leader cards")
    void loadLeaderCardTest() throws IOException {
        ArrayList<LeaderCard> cards = JsonToLeaderCard.readLeaderCards();
    }

    @Test
    @DisplayName("Checking correct leader cards ID")
    void correctIDTest() throws NoCardException{
        assertEquals(JsonToLeaderCard.getLeaderCard(1).getID(), 1);
        assertEquals(JsonToLeaderCard.getLeaderCard(5).getID(), 5);
        assertEquals(JsonToLeaderCard.getLeaderCard(16).getID(), 16);
        assertEquals(JsonToLeaderCard.getLeaderCard(3).getID(), 3);
        assertEquals(JsonToLeaderCard.getLeaderCard(8).getID(), 8);
        assertEquals(JsonToLeaderCard.getLeaderCard(13).getID(), 13);
    }

    @Test
    @DisplayName("Checking boarder leader cards' IDs")
    void incorrectIDsTest(){
        assertThrows(NoCardException.class, () ->
                JsonToLeaderCard.getLeaderCard(0));
        assertThrows(NoCardException.class, () ->
                JsonToLeaderCard.getLeaderCard(17));
    }

}