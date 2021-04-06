package it.polimi.ingsw.model.actionToken;

import it.polimi.ingsw.model.stubs.SingleFaithPathStub;
import it.polimi.ingsw.model.stubs.SingleplayerStub;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShuffleTokenTest {
    SingleFaithPathStub faithPath;
    SingleplayerStub singleplayer;

    @BeforeAll
    void init(){
        faithPath = new SingleFaithPathStub();
        singleplayer = new SingleplayerStub(null, faithPath);
    }

    @Test
    void faithPath(){
        faithPath.cross = 2;

        FaithForwardToken token = new FaithForwardToken();
        token.triggerEffect(singleplayer);

        assertTrue(faithPath.cross == 3);
    }

    @Test
    void shuffled(){
        singleplayer.shuffled = false;

        FaithForwardToken token = new FaithForwardToken();
        token.triggerEffect(singleplayer);

        assertTrue(singleplayer.shuffled);
    }
}