package it.polimi.ingsw.model.actionToken;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Identity;
import it.polimi.ingsw.model.exceptions.InvalidReadException;
import it.polimi.ingsw.model.exceptions.NoSuchPlayerException;
import it.polimi.ingsw.model.stubs.DevelopmentCardSetStub;
import it.polimi.ingsw.model.stubs.SingleplayerStub;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DiscardTokenTest {
    static DevelopmentCardSetStub set;
    static SingleplayerStub single;


    @BeforeAll
    static void init() throws IOException, InvalidReadException, NoSuchPlayerException {
        set = new DevelopmentCardSetStub();
        ArrayList<Identity> identities = new ArrayList<>();
        identities.add(new Identity("Test"));
        single = new SingleplayerStub(set, null, identities);
    }

    @Test
    void triggerEffect(){
        set.blueLevelOne = 10;
        set.blueLevelTwo = 10;
        set.blueLevelThree = 10;
        set.yellowLevelOne = 3;
        DiscardToken token = new DiscardToken(Color.BLUE);
        token.triggerEffect(single);

        assertTrue(set.blueLevelOne == 8 && set.blueLevelTwo == 10 && set.blueLevelThree == 10 && set.yellowLevelOne == 3);
    }

    @Test
    void zeroLevelOne(){
        set.blueLevelOne = 2;
        DiscardToken token = new DiscardToken(Color.BLUE);
        token.triggerEffect(single);

        assertTrue(set.blueLevelOne == 0);
    }

    @Test
    void noLevelOne(){
        set.blueLevelOne = 0;
        set.blueLevelTwo = 10;
        DiscardToken token = new DiscardToken(Color.BLUE);
        token.triggerEffect(single);
        assertTrue(set.blueLevelOne == 0 && set.blueLevelTwo == 8);
    }

    @Test
    void oneLevelOne(){
        set.blueLevelOne = 1;
        set.blueLevelTwo = 10;
        DiscardToken token = new DiscardToken(Color.BLUE);
        token.triggerEffect(single);

        assertTrue(set.blueLevelOne == 0 && set.blueLevelTwo == 9);
    }

    @Test
    void onlyTwoLeft(){
        set.blueLevelOne = 0;
        set.blueLevelTwo = 0;
        set.blueLevelThree = 2;
        DiscardToken token = new DiscardToken(Color.BLUE);
        token.triggerEffect(single);

        assertTrue(set.blueLevelOne == 0 && set.blueLevelTwo == 0 && set.blueLevelThree == 0);
    }

    @Test
    void onlyOneLeft(){
        set.blueLevelOne = 0;
        set.blueLevelTwo = 0;
        set.blueLevelThree = 1;
        DiscardToken token = new DiscardToken(Color.BLUE);
        token.triggerEffect(single);

        assertTrue(set.blueLevelOne == 0 && set.blueLevelTwo == 0 && set.blueLevelThree == 0);
    }

    @Test
    void gameEnded(){
        set.blueLevelOne = 0;
        set.blueLevelTwo = 0;
        set.blueLevelThree = 0;
        set.yellowLevelOne = 3;
        DiscardToken token = new DiscardToken(Color.BLUE);
        token.triggerEffect(single);

        assertTrue(set.blueLevelOne == 0 && set.blueLevelTwo == 0 && set.blueLevelThree == 0 && set.yellowLevelOne == 3);
    }
}