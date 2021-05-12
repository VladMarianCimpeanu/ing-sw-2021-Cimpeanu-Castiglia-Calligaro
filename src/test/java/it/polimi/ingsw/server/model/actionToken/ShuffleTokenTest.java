package it.polimi.ingsw.server.model.actionToken;

import it.polimi.ingsw.server.model.Identity;
import it.polimi.ingsw.server.model.exceptions.InvalidReadException;
import it.polimi.ingsw.server.model.exceptions.NoSuchPlayerException;
import it.polimi.ingsw.server.model.stubs.SingleFaithPathStub;
import it.polimi.ingsw.server.model.stubs.SingleplayerStub;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ShuffleTokenTest {
    static SingleFaithPathStub faithPath;
    static SingleplayerStub singleplayer;

    @BeforeAll
    static void init() throws IOException, InvalidReadException, NoSuchPlayerException {
        faithPath = new SingleFaithPathStub();
        ArrayList<Identity> identities = new ArrayList<>();
        identities.add(new Identity("Test"));
        singleplayer = new SingleplayerStub(null, faithPath, identities);
    }

    @Test
    void faithPath() throws NoSuchPlayerException {
        faithPath.cross = 2;

        ShuffleToken token = new ShuffleToken();
        token.triggerEffect(singleplayer);
        System.out.println(faithPath.cross);
        assertTrue(faithPath.cross == 3);
    }

    @Test
    void shuffled() throws NoSuchPlayerException {
        singleplayer.shuffled = false;

        ShuffleToken token = new ShuffleToken();
        token.triggerEffect(singleplayer);

        assertTrue(singleplayer.shuffled);
    }
}