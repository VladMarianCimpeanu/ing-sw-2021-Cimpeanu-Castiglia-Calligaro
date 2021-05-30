package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.exceptions.GameEndedException;
import it.polimi.ingsw.server.model.exceptions.InvalidStepsException;
import it.polimi.ingsw.server.model.exceptions.NoSuchPlayerException;
import it.polimi.ingsw.server.model.stubs.PlayerStub2;
import it.polimi.ingsw.server.model.stubs.VirtualViewStub;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MultiFaithPathTest {
    // ------------- TEST ASSIGN VICTORY POINTS --------------------


    @Test
    @DisplayName("Checking assign victory points can handle null exceptions")
    void addVictoryPointsToNull() throws NoSuchPlayerException {
        ArrayList<Player> currPlayers = new ArrayList<>();
        for(int i = 0; i < 3; i ++) currPlayers.add(new PlayerStub2(new Identity(Integer.valueOf(i).toString()), null));
        MultiFaithPath path = new MultiFaithPath(currPlayers);
        path.assignVictoryPoints(null);
        assertEquals(0, currPlayers.get(0).getVictoryPoints());
        assertEquals(0, currPlayers.get(1).getVictoryPoints());
        assertEquals(0, currPlayers.get(2).getVictoryPoints());
    }

    @Test
    @DisplayName("Checking assign victory points can handle not existing players ")
    void addVictoryPointsToNotExistingPlayer() {
        ArrayList<Player> currPlayers = new ArrayList<>();
        for(int i = 0; i < 3; i ++) currPlayers.add(new PlayerStub2(new Identity(Integer.valueOf(i).toString()), null));
        MultiFaithPath path = new MultiFaithPath(currPlayers);
        assertThrows( NoSuchPlayerException.class,
                () -> path.assignVictoryPoints(new Player(new Identity("not Playing"), null, null, null)));
        assertEquals(0, currPlayers.get(0).getVictoryPoints());
        assertEquals(0, currPlayers.get(1).getVictoryPoints());
        assertEquals(0, currPlayers.get(2).getVictoryPoints());
    }

    @Test
    @DisplayName("Checking assign victory points can work in edge zones")
    void assignVictoryPointsTest1() throws NoSuchPlayerException, InvalidStepsException, GameEndedException {
        ArrayList<Player> currPlayers = new ArrayList<>();
        for(int i = 0; i < 3; i ++) currPlayers.add(new PlayerStub2(new Identity(Integer.valueOf(i).toString()), null));
        MultiFaithPath path = new MultiFaithPath(currPlayers);
        path.subscribe(new VirtualViewStub());
        path.movePlayer(currPlayers.get(0), 3);
        path.movePlayer(currPlayers.get(1), 6);
        path.movePlayer(currPlayers.get(2), 9);
        for(int i = 0; i < 3; i ++) path.assignVictoryPoints(currPlayers.get(i));
        assertEquals(1, currPlayers.get(0).getVictoryPoints());
        assertEquals(4, currPlayers.get(1).getVictoryPoints());
        assertEquals(6, currPlayers.get(2).getVictoryPoints());
    }

    @Test
    @DisplayName("Checking assign victory points can work in normal situations")
    void assignVictoryPointsTest2() throws NoSuchPlayerException, InvalidStepsException, GameEndedException {
        ArrayList<Player> currPlayers = new ArrayList<>();
        for(int i = 0; i < 3; i ++) currPlayers.add(new PlayerStub2(new Identity(Integer.valueOf(i).toString()), null));
        MultiFaithPath path = new MultiFaithPath(currPlayers);
        path.subscribe(new VirtualViewStub());
        for(int i = 0; i < 3; i ++) path.movePlayer(currPlayers.get(i), i * 8 );
        for(int i = 0; i < 3; i ++) path.assignVictoryPoints(currPlayers.get(i));
        assertEquals(0, currPlayers.get(0).getVictoryPoints());
        assertEquals(4, currPlayers.get(1).getVictoryPoints());
        assertEquals(12, currPlayers.get(2).getVictoryPoints());
    }

    @Test
    @DisplayName("Checking assign victory points can work in edge zones *3*")
    void assignVictoryPointsTest3() throws NoSuchPlayerException, InvalidStepsException, GameEndedException {
        ArrayList<Player> currPlayers = new ArrayList<>();
        for(int i = 0; i < 3; i ++) currPlayers.add(new PlayerStub2(new Identity(Integer.valueOf(i).toString()), null));
        MultiFaithPath path = new MultiFaithPath(currPlayers);
        path.subscribe(new VirtualViewStub());
        path.movePlayer(currPlayers.get(0), 12);
        path.movePlayer(currPlayers.get(1), 15);
        path.movePlayer(currPlayers.get(2), 18);
        for(int i = 0; i < 3; i ++) path.assignVictoryPoints(currPlayers.get(i));
        assertEquals(11, currPlayers.get(0).getVictoryPoints());
        assertEquals(12, currPlayers.get(1).getVictoryPoints());
        assertEquals(15, currPlayers.get(2).getVictoryPoints());
    }

    @Test
    @DisplayName("Checking assign victory points can work in edge zones 4")
    void assignVictoryPointsTest4() throws NoSuchPlayerException, InvalidStepsException, GameEndedException {
        ArrayList<Player> currPlayers = new ArrayList<>();
        for(int i = 0; i < 3; i ++) currPlayers.add(new PlayerStub2(new Identity(Integer.valueOf(i).toString()), null));
        MultiFaithPath path = new MultiFaithPath(currPlayers);
        path.subscribe(new VirtualViewStub());
        path.movePlayer(currPlayers.get(0), 2);
        path.movePlayer(currPlayers.get(1), 5);
        path.movePlayer(currPlayers.get(2), 8);
        for(int i = 0; i < 3; i ++) path.assignVictoryPoints(currPlayers.get(i));
        assertEquals(0, currPlayers.get(0).getVictoryPoints());
        assertEquals(3, currPlayers.get(1).getVictoryPoints());
        assertEquals(4, currPlayers.get(2).getVictoryPoints());
    }

    // ------------- TEST MOVE PLAYER --------------------


    @Test
    @DisplayName("Checking move player can handle negative steps *1*")
    void moveBackward1() throws InvalidStepsException, NoSuchPlayerException, GameEndedException {
        ArrayList<Player> currPlayers = new ArrayList<>();
        for (int i = 0; i < 3; i++) currPlayers.add(new PlayerStub2(new Identity(Integer.valueOf(i).toString()), null));
        MultiFaithPath path = new MultiFaithPath(currPlayers);
        path.subscribe(new VirtualViewStub());
        for (int i = 0; i < 3; i++) path.movePlayer(currPlayers.get(i), i * 8);
        assertThrows(InvalidStepsException.class,
                () -> path.movePlayer(currPlayers.get(0), -1));
        assertEquals(0, path.getPlayerPosition(currPlayers.get(0)));
    }

    @Test
    @DisplayName("Checking move player can handle negative steps *2*")
    void moveBackward2() throws InvalidStepsException, NoSuchPlayerException, GameEndedException {
        ArrayList<Player> currPlayers = new ArrayList<>();
        for (int i = 0; i < 3; i++) currPlayers.add(new PlayerStub2(new Identity(Integer.valueOf(i).toString()), null));
        MultiFaithPath path = new MultiFaithPath(currPlayers);
        path.subscribe(new VirtualViewStub());
        for (int i = 0; i < 3; i++) path.movePlayer(currPlayers.get(i), i * 8);
        int currPosition = path.getPlayerPosition(currPlayers.get(2));
        assertThrows(InvalidStepsException.class,
                () -> path.movePlayer(currPlayers.get(2), -4));
        assertEquals(currPosition, path.getPlayerPosition(currPlayers.get(2)));
    }

    @Test
    @DisplayName("Checking move player can handle not existing players")
    void moveNoOne() throws InvalidStepsException, NoSuchPlayerException, GameEndedException {
        ArrayList<Player> currPlayers = new ArrayList<>();
        for (int i = 0; i < 3; i++) currPlayers.add(new PlayerStub2(new Identity(Integer.valueOf(i).toString()), null));
        MultiFaithPath path = new MultiFaithPath(currPlayers);
        path.subscribe(new VirtualViewStub());
        for (int i = 0; i < 3; i++) path.movePlayer(currPlayers.get(i), i * 8);
        int currPlayersPositionSize = path.getAllPositions().size();
        assertThrows(NoSuchPlayerException.class,
                () -> path.movePlayer(new PlayerStub2(new Identity("I'm not playing"),null), 2));
        assertEquals(currPlayersPositionSize, path.getAllPositions().size());
    }

    @Test
    @DisplayName("Checking move player can handle null players")
    void moveNull() throws InvalidStepsException, NoSuchPlayerException, GameEndedException {
        ArrayList<Player> currPlayers = new ArrayList<>();
        for (int i = 0; i < 3; i++) currPlayers.add(new PlayerStub2(new Identity(Integer.valueOf(i).toString()), null));
        MultiFaithPath path = new MultiFaithPath(currPlayers);
        path.subscribe(new VirtualViewStub());
        for (int i = 0; i < 3; i++) path.movePlayer(currPlayers.get(i), i * 8);
        int currPlayersPositionSize = path.getAllPositions().size();
        assertThrows(NoSuchPlayerException.class,
                () -> path.movePlayer(null, 2));
        assertEquals(currPlayersPositionSize, path.getAllPositions().size());
    }

    @Test
    @DisplayName("Checking move player can handle overflow steps")
    void moveFarAway() throws InvalidStepsException, NoSuchPlayerException, GameEndedException {
        ArrayList<Player> currPlayers = new ArrayList<>();
        for (int i = 0; i < 3; i++) currPlayers.add(new PlayerStub2(new Identity(Integer.valueOf(i).toString()), null));
        MultiFaithPath path = new MultiFaithPath(currPlayers);
        path.subscribe(new VirtualViewStub());
        for (int i = 0; i < 3; i++) path.movePlayer(currPlayers.get(i), i * 8);
        assertThrows(GameEndedException.class,
                () ->path.movePlayer(currPlayers.get(2), 30));
        assertEquals(24, path.getPlayerPosition(currPlayers.get(2)));



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
    void movePlayerTest1() throws InvalidStepsException, NoSuchPlayerException, GameEndedException {
        ArrayList<Player> currPlayers = new ArrayList<>();
        for (int i = 0; i < 3; i++) currPlayers.add(new PlayerStub2(new Identity(Integer.valueOf(i).toString()), null));
        MultiFaithPath path = new MultiFaithPath(currPlayers);
        path.subscribe(new VirtualViewStub());
        path.movePlayer(currPlayers.get(0), 3);
        path.movePlayer(currPlayers.get(1), 5);
        path.movePlayer(currPlayers.get(2), 7);

        assertEquals(0, currPlayers.get(0).getVictoryPoints());
        assertEquals(0, currPlayers.get(1).getVictoryPoints());
        assertEquals(0, currPlayers.get(2).getVictoryPoints());
    }

    @Test
    @DisplayName("Checking move player can trigger correctly pope meetings *2*")
    void movePlayerTest2() throws InvalidStepsException, NoSuchPlayerException, GameEndedException {
        ArrayList<Player> currPlayers = new ArrayList<>();
        for (int i = 0; i < 3; i++) currPlayers.add(new PlayerStub2(new Identity(Integer.valueOf(i).toString()), null));
        MultiFaithPath path = new MultiFaithPath(currPlayers);
        path.subscribe(new VirtualViewStub());
        path.movePlayer(currPlayers.get(0), 0);
        path.movePlayer(currPlayers.get(1), 5);
        path.movePlayer(currPlayers.get(2), 8);

        assertEquals(0, currPlayers.get(0).getVictoryPoints());
        assertEquals(2, currPlayers.get(1).getVictoryPoints());
        assertEquals(2, currPlayers.get(2).getVictoryPoints());
    }

    @Test
    @DisplayName("Checking move player can trigger correctly pope meetings *3*")
    void movePlayerTest3() throws InvalidStepsException, NoSuchPlayerException, GameEndedException {
        ArrayList<Player> currPlayers = new ArrayList<>();
        for (int i = 0; i < 3; i++) currPlayers.add(new PlayerStub2(new Identity(Integer.valueOf(i).toString()), null));
        MultiFaithPath path = new MultiFaithPath(currPlayers);
        path.subscribe(new VirtualViewStub());
        path.movePlayer(currPlayers.get(0), 0);
        path.movePlayer(currPlayers.get(1), 5);
        path.movePlayer(currPlayers.get(2), 10);

        assertEquals(0, currPlayers.get(0).getVictoryPoints());
        assertEquals(2, currPlayers.get(1).getVictoryPoints());
        assertEquals(2, currPlayers.get(2).getVictoryPoints());
        assertEquals(10, path.getPlayerPosition(currPlayers.get(2)));
    }

    @Test
    @DisplayName("Checking move player can trigger correctly pope meetings *4*")
    void movePlayerTest4() throws InvalidStepsException, NoSuchPlayerException, GameEndedException {
        ArrayList<Player> currPlayers = new ArrayList<>();
        for (int i = 0; i < 3; i++) currPlayers.add(new PlayerStub2(new Identity(Integer.valueOf(i).toString()), null));
        MultiFaithPath path = new MultiFaithPath(currPlayers);
        path.subscribe(new VirtualViewStub());
        path.movePlayer(currPlayers.get(0), 3);
        path.movePlayer(currPlayers.get(1), 5);
        path.movePlayer(currPlayers.get(2), 10);

        assertEquals(0, currPlayers.get(0).getVictoryPoints());
        assertEquals(2, currPlayers.get(1).getVictoryPoints());
        assertEquals(2, currPlayers.get(2).getVictoryPoints());
        assertEquals(10, path.getPlayerPosition(currPlayers.get(2)));
    }

    @Test
    @DisplayName("Checking once a pope meeting is triggered, it can not be triggered again")
    void movePlayerTest5() throws InvalidStepsException, NoSuchPlayerException, GameEndedException {
        ArrayList<Player> currPlayers = new ArrayList<>();
        for (int i = 0; i < 3; i++) currPlayers.add(new PlayerStub2(new Identity(Integer.valueOf(i).toString()), null));
        MultiFaithPath path = new MultiFaithPath(currPlayers);
        path.subscribe(new VirtualViewStub());
        path.movePlayer(currPlayers.get(0), 3);
        path.movePlayer(currPlayers.get(1), 8);
        path.movePlayer(currPlayers.get(2), 2);

        assertEquals(0, currPlayers.get(0).getVictoryPoints());
        assertEquals(2, currPlayers.get(1).getVictoryPoints());
        assertEquals(0, currPlayers.get(2).getVictoryPoints());
    }

    @Test
    @DisplayName("Checking movePlayer can work in normal conditions")
    void movePlayerTest() throws InvalidStepsException, NoSuchPlayerException, GameEndedException {
        ArrayList<Player> currPlayers = new ArrayList<>();
        for (int i = 0; i < 3; i++) currPlayers.add(new PlayerStub2(new Identity(Integer.valueOf(i).toString()), null));
        MultiFaithPath path = new MultiFaithPath(currPlayers);
        path.subscribe(new VirtualViewStub());
        path.movePlayer(currPlayers.get(0), 3);
        path.movePlayer(currPlayers.get(1), 8);
        path.movePlayer(currPlayers.get(2), 2);

        assertEquals(3, path.getPlayerPosition(currPlayers.get(0)));
        assertEquals(8, path.getPlayerPosition(currPlayers.get(1)));
        assertEquals(2, path.getPlayerPosition(currPlayers.get(2)));
    }

    // ------------- TEST GET PLAYER POSITION --------------------


    @Test
    @DisplayName("Checking getPlayerPosition can handle null players")
    void getNullPlayer() {
        ArrayList<Player> currPlayers = new ArrayList<>();
        for (int i = 0; i < 3; i++) currPlayers.add(new PlayerStub2(new Identity(Integer.valueOf(i).toString()), null));
        MultiFaithPath path = new MultiFaithPath(currPlayers);
        assertThrows(NoSuchPlayerException.class,
                () -> path.getPlayerPosition(null));
    }

    @Test
    @DisplayName("Checking getPlayerPosition can handle not existing players")
    void getNotExistingPlayer() {
        ArrayList<Player> currPlayers = new ArrayList<>();
        for (int i = 0; i < 3; i++) currPlayers.add(new PlayerStub2(new Identity(Integer.valueOf(i).toString()), null));
        MultiFaithPath path = new MultiFaithPath(currPlayers);
        assertThrows(NoSuchPlayerException.class,
                () -> path.getPlayerPosition(new PlayerStub2(new Identity("Where am I?"), null)));
    }

    // ------------- TEST MOVE OPPONENTS --------------------

    @Test
    @DisplayName("Checking moveOpponents can handle not existing player")
    void moveOpponentsOfNotExistingPlayer() throws InvalidStepsException, NoSuchPlayerException, GameEndedException {
        ArrayList<Player> currPlayers = new ArrayList<>();
        for (int i = 0; i < 3; i ++) currPlayers.add(new PlayerStub2(new Identity(Integer.valueOf(i).toString()), null));
        MultiFaithPath path = new MultiFaithPath(currPlayers);
        path.subscribe(new VirtualViewStub());
        path.movePlayer(currPlayers.get(0), 1);
        path.movePlayer(currPlayers.get(1), 2);
        path.movePlayer(currPlayers.get(2), 0);

        assertThrows(NoSuchPlayerException.class,
                () -> path.moveOpponents(new PlayerStub2(new Identity("Not Playing here"), null)));
        assertEquals(1, path.getPlayerPosition(currPlayers.get(0)));
        assertEquals(2, path.getPlayerPosition(currPlayers.get(1)));
        assertEquals(0, path.getPlayerPosition(currPlayers.get(2)));
        assertEquals(3, path.getAllPositions().size());
    }

    @Test
    @DisplayName("Checking moveOpponents can handle null")
    void moveOpponentsOfNull() throws InvalidStepsException, NoSuchPlayerException, GameEndedException {
        ArrayList<Player> currPlayers = new ArrayList<>();
        for (int i = 0; i < 3; i ++) currPlayers.add(new PlayerStub2(new Identity(Integer.valueOf(i).toString()), null));
        MultiFaithPath path = new MultiFaithPath(currPlayers);
        path.subscribe(new VirtualViewStub());
        path.movePlayer(currPlayers.get(0), 1);
        path.movePlayer(currPlayers.get(1), 2);
        path.movePlayer(currPlayers.get(2), 0);

        assertThrows(NoSuchPlayerException.class,
                () -> path.moveOpponents(null));
        assertEquals(1, path.getPlayerPosition(currPlayers.get(0)));
        assertEquals(2, path.getPlayerPosition(currPlayers.get(1)));
        assertEquals(0, path.getPlayerPosition(currPlayers.get(2)));
        assertEquals(3, path.getAllPositions().size());
    }

    @Test
    @DisplayName("Checking move opponents can handle trigger pope positions")
    void moveOpponentsPopeMeeting1() throws InvalidStepsException, NoSuchPlayerException, GameEndedException {
        ArrayList<Player> currPlayers = new ArrayList<>();
        for (int i = 0; i < 3; i ++) currPlayers.add(new PlayerStub2(new Identity(Integer.valueOf(i).toString()), null));
        MultiFaithPath path = new MultiFaithPath(currPlayers);
        path.subscribe(new VirtualViewStub());
        path.movePlayer(currPlayers.get(0), 4);
        path.movePlayer(currPlayers.get(1), 7);
        path.movePlayer(currPlayers.get(2), 1);
        path.moveOpponents(currPlayers.get(2));

        assertEquals(2, currPlayers.get(0).getVictoryPoints());
        assertEquals(2, currPlayers.get(1).getVictoryPoints());
        assertEquals(0, currPlayers.get(2).getVictoryPoints());

        assertEquals(5, path.getPlayerPosition(currPlayers.get(0)));
        assertEquals(8, path.getPlayerPosition(currPlayers.get(1)));
        assertEquals(1, path.getPlayerPosition(currPlayers.get(2)));    //changed, was expected: 2
    }

    @Test
    @DisplayName("Checking move opponents can work in normal conditions")
    void moveOpponents() throws InvalidStepsException, NoSuchPlayerException, GameEndedException {
        ArrayList<Player> currPlayers = new ArrayList<>();
        for (int i = 0; i < 3; i ++) currPlayers.add(new PlayerStub2(new Identity(Integer.valueOf(i).toString()), null));
        MultiFaithPath path = new MultiFaithPath(currPlayers);
        path.subscribe(new VirtualViewStub());
        path.movePlayer(currPlayers.get(0), 0);
        path.movePlayer(currPlayers.get(1), 3);
        path.movePlayer(currPlayers.get(2), 1);

        path.moveOpponents(currPlayers.get(0));
        assertEquals(0, path.getPlayerPosition(currPlayers.get(0)));
        assertEquals(4, path.getPlayerPosition(currPlayers.get(1)));
        assertEquals(2, path.getPlayerPosition(currPlayers.get(2)));
    }

    @Test
    @DisplayName("Checking move opponents can work in normal conditions *2*")
    void moveOpponents2() throws InvalidStepsException, NoSuchPlayerException, GameEndedException {
        ArrayList<Player> currPlayers = new ArrayList<>();
        for (int i = 0; i < 3; i ++) currPlayers.add(new PlayerStub2(new Identity(Integer.valueOf(i).toString()), null));
        MultiFaithPath path = new MultiFaithPath(currPlayers);
        path.subscribe(new VirtualViewStub());
        path.movePlayer(currPlayers.get(0), 0);
        path.movePlayer(currPlayers.get(1), 3);
        path.movePlayer(currPlayers.get(2), 1);

        path.moveOpponents(currPlayers.get(1));
        assertEquals(1, path.getPlayerPosition(currPlayers.get(0)));
        assertEquals(3, path.getPlayerPosition(currPlayers.get(1)));
        assertEquals(2, path.getPlayerPosition(currPlayers.get(2)));
    }

}