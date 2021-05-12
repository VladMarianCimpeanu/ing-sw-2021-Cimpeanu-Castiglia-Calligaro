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

class FaithForwardTokenTest {
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
    void triggerEffect(){
        faithPath.cross = 2;

        FaithForwardToken token = new FaithForwardToken();
        token.triggerEffect(singleplayer);

        assertTrue(faithPath.cross == 4);
    }
}