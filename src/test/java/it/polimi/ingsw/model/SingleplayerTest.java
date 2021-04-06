package it.polimi.ingsw.model;

import it.polimi.ingsw.model.actionToken.ActionToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SingleplayerTest {
    private Singleplayer game;
    @BeforeEach
    void init(){
        ArrayList<Identity> identities = new ArrayList<>();
        identities.add(new Identity(""));
        game = new Singleplayer(identities);  //better creating stub because of not handling null cases
    }

    @Test
    void drawToken() {
        //assuming the card draw out is placed at the bottom of the current set of ActionTokens
        ActionToken top = game.getAvailableActionTokens().get(0);
        int pre_size = game.getAvailableActionTokens().size();
        game.drawToken();
        assertTrue(top == game.getAvailableActionTokens().get(game.getAvailableActionTokens().size()-1));
        assertTrue(pre_size == game.getAvailableActionTokens().size());
    }

    /**
     * check if the position of the cards inside the deck is changed after shuffle
     */
    @Test
    void shuffleToken() {
        Map<ActionToken, Integer> pre_positions = new HashMap<ActionToken, Integer>();
        for(ActionToken token : game.getAvailableActionTokens())
            pre_positions.put(token, game.getAvailableActionTokens().indexOf(token));
        int pre_size = game.getAvailableActionTokens().size();
        game.shuffleToken();
        Map<ActionToken, Integer> post_positions = new HashMap<ActionToken, Integer>();
        for(ActionToken token : game.getAvailableActionTokens())
            post_positions.put(token, game.getAvailableActionTokens().indexOf(token));
        int post_size = game.getAvailableActionTokens().size();
        assertNotEquals(pre_positions, post_positions);
        assertEquals(pre_size, post_size);
    }
}