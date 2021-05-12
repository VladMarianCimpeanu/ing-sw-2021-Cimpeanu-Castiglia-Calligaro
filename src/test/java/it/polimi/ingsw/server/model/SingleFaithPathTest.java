package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.exceptions.InvalidStepsException;
import it.polimi.ingsw.server.model.exceptions.NoSuchPlayerException;
import it.polimi.ingsw.server.model.stubs.PlayerStub2;
import it.polimi.ingsw.server.model.stubs.VirtualViewStub;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class SingleFaithPathTest {

    // ------------- TEST ASSIGN VICTORY POINTS --------------------


    @Test
    @DisplayName("Checking assign victory points can handle null exceptions")
    void addVictoryPointsToNull() {
        Player player = new PlayerStub2(new Identity("Alone"), null);
        SingleFaithPath path = new SingleFaithPath(player);
        assertThrows(NoSuchPlayerException.class,
                () -> path.assignVictoryPoints(null));
        assertEquals(0, player.getVictoryPoints());
    }

    @Test
    @DisplayName("Checking assign victory points can handle not existing players ")
    void addVictoryPointsToNotExistingPlayer() {
        Player player = new PlayerStub2(new Identity("Alone"), null);
        SingleFaithPath path = new SingleFaithPath(player);
        assertThrows(NoSuchPlayerException.class,
                () -> path.assignVictoryPoints(new PlayerStub2(new Identity("another one"), null)));
        assertEquals(0, player.getVictoryPoints());
    }

    @Test
    @DisplayName("Checking assign victory points can work in edge zones *1*")
    void assignVictoryPointsTest1() throws NoSuchPlayerException, InvalidStepsException {
        Player player = new PlayerStub2(new Identity("Alone"), null);
        SingleFaithPath path = new SingleFaithPath(player);
        path.subscribe(new VirtualViewStub());
        path.movePlayer(player, 3);
        path.assignVictoryPoints(player);
        assertEquals(1, player.getVictoryPoints());
    }

    @Test
    @DisplayName("Checking assign victory points can work in edge zones *2*")
    void assignVictoryPointsTest2() throws NoSuchPlayerException, InvalidStepsException {
        Player player = new PlayerStub2(new Identity("Alone"), null);
        SingleFaithPath path = new SingleFaithPath(player);
        path.subscribe(new VirtualViewStub());
        path.movePlayer(player, 2);
        path.assignVictoryPoints(player);
        assertEquals(0, player.getVictoryPoints());
    }

    @Test
    @DisplayName("Checking assign victory points can work in edge zones *3*")
    void assignVictoryPointsTest3() throws NoSuchPlayerException, InvalidStepsException {
        Player player = new PlayerStub2(new Identity("Alone"), null);
        SingleFaithPath path = new SingleFaithPath(player);
        path.subscribe(new VirtualViewStub());
        path.movePlayer(player, 9);
        path.assignVictoryPoints(player);
        assertEquals(6, player.getVictoryPoints());
    }

    @Test
    @DisplayName("Checking assign victory points can work in edge zones *4*")
    void assignVictoryPointsTest4() throws NoSuchPlayerException, InvalidStepsException {
        Player player = new PlayerStub2(new Identity("Alone"), null);
        SingleFaithPath path = new SingleFaithPath(player);
        path.subscribe(new VirtualViewStub());
        path.movePlayer(player, 8);
        path.assignVictoryPoints(player);
        assertEquals(4, player.getVictoryPoints());
    }

    // ------------- TEST MOVE PLAYER --------------------


    @Test
    @DisplayName("Checking move player can handle negative steps *1*")
    void moveBackward1() throws NoSuchPlayerException {
        Player player = new PlayerStub2(new Identity("Alone"), null);
        SingleFaithPath path = new SingleFaithPath(player);
        assertThrows(InvalidStepsException.class,
                () -> path.movePlayer(player, -1));
        assertEquals(0, path.getPlayerPosition(player));
    }

    @Test
    @DisplayName("Checking move player can handle negative steps *2*")
    void moveBackward2() throws InvalidStepsException, NoSuchPlayerException {
        Player player = new PlayerStub2(new Identity("Alone"), null);
        SingleFaithPath path = new SingleFaithPath(player);
        path.subscribe(new VirtualViewStub());
        path.movePlayer(player, 2);
        assertThrows(InvalidStepsException.class,
                () -> path.movePlayer(player, -1));
        assertEquals(2, path.getPlayerPosition(player));
    }

    @Test
    @DisplayName("Checking move player can handle not existing players")
    void moveNoOne() {
        Player player = new PlayerStub2(new Identity("Alone"), null);
        SingleFaithPath path = new SingleFaithPath(player);
        int currPlayersPositionSize = path.getAllPositions().size();
        assertThrows(NoSuchPlayerException.class,
                () -> path.movePlayer(new PlayerStub2(new Identity("I'm not playing"),null), 2));
        assertEquals(currPlayersPositionSize, path.getAllPositions().size());
    }

    @Test
    @DisplayName("Checking move player can handle null players")
    void moveNull() {
        Player player = new PlayerStub2(new Identity("Alone"), null);
        SingleFaithPath path = new SingleFaithPath(player);
        int currPlayersPositionSize = path.getAllPositions().size();
        assertThrows(NoSuchPlayerException.class,
                () -> path.movePlayer(null,2));
        assertEquals(currPlayersPositionSize, path.getAllPositions().size());
    }

    @Test
    @DisplayName("Checking move player can handle overflow steps")
    void moveFarAway() throws InvalidStepsException, NoSuchPlayerException {
        Player player = new PlayerStub2(new Identity("Alone"), null);
        SingleFaithPath path = new SingleFaithPath(player);
        path.subscribe(new VirtualViewStub());
        path.movePlayer(player, 30);
        assertEquals(24, path.getPlayerPosition(player));



        //@@@@@@@@@@@@@@@@@ CHECK GAME IS GOING TO END @@@@@@@@@@@@@@@@@@@@@@@@@@
        //@@@@@@@@@@@@@@@@@ CHECK GAME IS GOING TO END @@@@@@@@@@@@@@@@@@@@@@@@@@
        //@@@@@@@@@@@@@@@@@ CHECK GAME IS GOING TO END @@@@@@@@@@@@@@@@@@@@@@@@@@
        //@@@@@@@@@@@@@@@@@ CHECK GAME IS GOING TO END @@@@@@@@@@@@@@@@@@@@@@@@@@
        //@@@@@@@@@@@@@@@@@ CHECK GAME IS GOING TO END @@@@@@@@@@@@@@@@@@@@@@@@@@
        //@@@@@@@@@@@@@@@@@ CHECK GAME IS GOING TO END @@@@@@@@@@@@@@@@@@@@@@@@@@
        //@@@@@@@@@@@@@@@@@ CHECK GAME IS GOING TO END @@@@@@@@@@@@@@@@@@@@@@@@@@
        //@@@@@@@@@@@@@@@@@ CHECK GAME IS GOING TO END @@@@@@@@@@@@@@@@@@@@@@@@@@
        //@@@@@@@@@@@@@@@@@ CHECK GAME IS GOING TO END @@@@@@@@@@@@@@@@@@@@@@@@@@
        //@@@@@@@@@@@@@@@@@ CHECK GAME IS GOING TO END @@@@@@@@@@@@@@@@@@@@@@@@@@         //@@@@@@@@@@@@@@@@@ CHECK GAME IS GOING TO END @@@@@@@@@@@@@@@@@@@@@@@@@@
        //        //@@@@@@@@@@@@@@@@@ CHECK GAME IS GOING TO END @@@@@@@@@@@@@@@@@@@@@@@@@@
        //        //@@@@@@@@@@@@@@@@@ CHECK GAME IS GOING TO END @@@@@@@@@@@@@@@@@@@@@@@@@@
        //        //@@@@@@@@@@@@@@@@@ CHECK GAME IS GOING TO END @@@@@@@@@@@@@@@@@@@@@@@@@@
        //        //@@@@@@@@@@@@@@@@@ CHECK GAME IS GOING TO END @@@@@@@@@@@@@@@@@@@@@@@@@@


    }

    @Test
    @DisplayName("Checking move player can trigger correctly pope meetings *1*")
    void movePlayerTest1() throws InvalidStepsException, NoSuchPlayerException {
        Player player = new PlayerStub2(new Identity("Alone"), null);
        SingleFaithPath path = new SingleFaithPath(player);
        path.subscribe(new VirtualViewStub());
        path.movePlayer(player, 8);

        assertEquals(2, player.getVictoryPoints());
    }

    @Test
    @DisplayName("Checking move player can trigger correctly pope meetings *2*")
    void movePlayerTest2() throws InvalidStepsException, NoSuchPlayerException {
        Player player = new PlayerStub2(new Identity("Alone"), null);
        SingleFaithPath path = new SingleFaithPath(player);
        path.subscribe(new VirtualViewStub());
        path.movePlayer(player, 9);

        assertEquals(2, player.getVictoryPoints());
    }

    @Test
    @DisplayName("Checking move player can trigger correctly pope meetings *3*")
    void movePlayerTest3() throws InvalidStepsException, NoSuchPlayerException {
        Player player = new PlayerStub2(new Identity("Alone"), null);
        SingleFaithPath path = new SingleFaithPath(player);
        path.subscribe(new VirtualViewStub());
        path.movePlayer(player, 7);

        assertEquals(0, player.getVictoryPoints());
    }

    @Test
    @DisplayName("Checking once a pope meeting is triggered, it can not be triggered again")
    void movePlayerPopeMeeting1() throws InvalidStepsException, NoSuchPlayerException {
        Player player = new PlayerStub2(new Identity("Alone"), null);
        SingleFaithPath path = new SingleFaithPath(player);
        path.subscribe(new VirtualViewStub());
        path.moveBlackCross(8);

        assertEquals(0, player.getVictoryPoints());
        path.moveBlackCross(2);
        path.movePlayer(player,8);
        assertEquals(0, player.getVictoryPoints());
    }

    @Test
    @DisplayName("Checking once a pope meeting is triggered, it can not be triggered again")
    void movePlayerPopeMeeting2() throws InvalidStepsException, NoSuchPlayerException {
        Player player = new PlayerStub2(new Identity("Alone"), null);
        SingleFaithPath path = new SingleFaithPath(player);
        path.subscribe(new VirtualViewStub());
        path.movePlayer(player,8);

        assertEquals(2, player.getVictoryPoints());
        path.movePlayer(player,2);
        path.moveBlackCross(8);
        assertEquals(2, player.getVictoryPoints());
    }

    @Test
    @DisplayName("Checking movePlayer can work in normal conditions")
    void movePlayerTest() throws InvalidStepsException, NoSuchPlayerException {
        Player player = new PlayerStub2(new Identity("Alone"), null);
        SingleFaithPath path = new SingleFaithPath(player);
        path.subscribe(new VirtualViewStub());
        path.movePlayer(player, 3);

        assertEquals(3, path.getPlayerPosition(player));
    }

    // ------------- TEST GET PLAYER POSITION --------------------


    @Test
    @DisplayName("Checking getPlayerPosition can handle null players")
    void getNullPlayer() {
        Player player = new PlayerStub2(new Identity("Alone"), null);
        SingleFaithPath path = new SingleFaithPath(player);
        assertThrows(NoSuchPlayerException.class,
                () -> path.getPlayerPosition(null));
    }

    @Test
    @DisplayName("Checking getPlayerPosition can handle not existing players")
    void getNotExistingPlayer() {
        Player player = new PlayerStub2(new Identity("Alone"), null);
        SingleFaithPath path = new SingleFaithPath(player);
        assertThrows(NoSuchPlayerException.class,
                () -> path.getPlayerPosition(new PlayerStub2(new Identity("Where am I?"), null)));
    }

    // ------------- TEST MOVE OPPONENTS --------------------

    @Test
    @DisplayName("Checking moveOpponents can handle not existing player")
    void moveOpponentsOfNotExistingPlayer() throws NoSuchPlayerException {
        Player player = new PlayerStub2(new Identity("Alone"), null);
        SingleFaithPath path = new SingleFaithPath(player);
        assertThrows(NoSuchPlayerException.class,
                () -> path.moveOpponents(new PlayerStub2(new Identity("Not Playing here"), null)));
        assertEquals(0, path.getPlayerPosition(player));
    }

    @Test
    @DisplayName("Checking moveOpponents can handle null")
    void moveOpponentsOfNull() throws NoSuchPlayerException {
        Player player = new PlayerStub2(new Identity("Alone"), null);
        SingleFaithPath path = new SingleFaithPath(player);
        assertThrows(NoSuchPlayerException.class,
                () -> path.moveOpponents(null));
        assertEquals(0, path.getPlayerPosition(player));
    }

    @Test
    @DisplayName("Checking move opponents can handle trigger pope positions *1*")
    void moveOpponentsPopeMeeting1() throws InvalidStepsException, NoSuchPlayerException {
        Player player = new PlayerStub2(new Identity("Alone"), null);
        SingleFaithPath path = new SingleFaithPath(player);
        path.subscribe(new VirtualViewStub());
        path.movePlayer(player, 6);
        path.moveBlackCross(7);
        path.moveOpponents(player);

        assertEquals(2, player.getVictoryPoints());
    }

    @Test
    @DisplayName("Checking move opponents can handle trigger pope positions *2*")
    void moveOpponentsPopeMeeting2() throws InvalidStepsException, NoSuchPlayerException {
        Player player = new PlayerStub2(new Identity("Alone"), null);
        SingleFaithPath path = new SingleFaithPath(player);
        path.subscribe(new VirtualViewStub());
        path.movePlayer(player, 3);
        path.moveBlackCross(7);
        path.moveOpponents(player);

        assertEquals(0, player.getVictoryPoints());
    }

    @Test
    @DisplayName("Checking move opponents can handle trigger pope positions *3*")
    void moveOpponentsPopeMeeting3() throws InvalidStepsException, NoSuchPlayerException {
        Player player = new PlayerStub2(new Identity("Alone"), null);
        SingleFaithPath path = new SingleFaithPath(player);
        path.subscribe(new VirtualViewStub());
        path.movePlayer(player, 4);
        path.moveBlackCross(7);
        path.moveOpponents(player);

        assertEquals(0, player.getVictoryPoints());
    }

    @Test
    @DisplayName("Checking move black cross can handle negative steps")
    void moveBlackCrossBackward()  {
        Player player = new PlayerStub2(new Identity("Alone"), null);
        SingleFaithPath path = new SingleFaithPath(player);
        path.moveBlackCross(-1);
        assertEquals(0, path.getBlackCrossPosition());
    }

    @Test
    @DisplayName("Checking move black cross can work in normal conditions")
    void moveBlackCrossTest() {
        Player player = new PlayerStub2(new Identity("Alone"), null);
        SingleFaithPath path = new SingleFaithPath(player);
        path.moveBlackCross(2);
        assertEquals(2, path.getBlackCrossPosition());
    }
}