package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.actionToken.ActionToken;
import it.polimi.ingsw.server.model.actionToken.ShuffleToken;
import it.polimi.ingsw.server.model.exceptions.InvalidReadException;
import it.polimi.ingsw.server.model.exceptions.NoSuchPlayerException;
import it.polimi.ingsw.server.model.stubs.VirtualViewStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SingleplayerTest {
    private Singleplayer game;
    @BeforeEach
    void init() throws IOException, InvalidReadException, NoSuchPlayerException {
        ArrayList<Identity> identities = new ArrayList<>();
        identities.add(new Identity(""));
        game = new Singleplayer(identities);  //better creating stub because of not handling null cases
        game.getDevelopmentCardSet().subscribe(new VirtualViewStub());
        game.getFaithPath().subscribe(new VirtualViewStub());
        game.subscribe(new VirtualViewStub());
    }

    @Test
    void drawToken() throws NoSuchPlayerException {
        //assuming the card draw out is placed at the bottom of the current set of ActionTokens
        int pre_size = game.getAvailableActionTokens().size();
        ActionToken top = game.getAvailableActionTokens().get(pre_size - 1);
        game.drawToken();
        if(!(top instanceof ShuffleToken))
            assertSame(top, game.getAvailableActionTokens().get(0));
        assertEquals(pre_size, game.getAvailableActionTokens().size());
    }

    /**
     * check if the position of the cards inside the deck is changed after shuffle
     */
    @Test
    void shuffleToken() {
        Map<ActionToken, Integer> pre_positions = new HashMap<>();
        for(ActionToken token : game.getAvailableActionTokens())
            pre_positions.put(token, game.getAvailableActionTokens().indexOf(token));
        int pre_size = game.getAvailableActionTokens().size();
        game.shuffleToken();
        Map<ActionToken, Integer> post_positions = new HashMap<>();
        for(ActionToken token : game.getAvailableActionTokens())
            post_positions.put(token, game.getAvailableActionTokens().indexOf(token));
        int post_size = game.getAvailableActionTokens().size();
        assertEquals(pre_size, post_size);
    }
}