package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.model.stubs.GameStub2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class WaitingRoomTest {


    //------------- TEST ADD USER --------------------

    @Test
    @DisplayName("Checking addUser can handle normal operations")
    void addUser() throws InvalidGameModeException, InvalidNicknameException, ExistingNicknameException {
        WaitingRoom waitingRoom = new WaitingRoom();
        waitingRoom.setGameMode(4);
        waitingRoom.addUser("First to log!");
        waitingRoom.addUser("Second to log!");
        waitingRoom.addUser("Third to log!");

        assertEquals(3, waitingRoom.getWaitingUsers().size());
        ArrayList<Identity> currentUsers =  waitingRoom.getWaitingUsers();
        assertEquals("First to log!", currentUsers.get(0).getNickname());
        assertEquals("Second to log!", currentUsers.get(1).getNickname());
        assertEquals("Third to log!", currentUsers.get(2).getNickname());
    }

    @Test
    @DisplayName("Checking addUser resets autonomously after adding the last user")
    void addLastUser() throws InvalidGameModeException, InvalidNicknameException, ExistingNicknameException {
        WaitingRoom waitingRoom = new WaitingRoom();
        waitingRoom.setGameMode(2);
        waitingRoom.addUser("First to log!");
        waitingRoom.addUser("Second to log!");
        assertEquals(0, waitingRoom.getWaitingUsers().size());
    }

    @Test
    @DisplayName("Checking addUser can discard logRequests from users with a nickname already used by an other user")
    void addExistingUser() throws InvalidGameModeException, InvalidNicknameException, ExistingNicknameException {
        WaitingRoom waitingRoom = new WaitingRoom();
        waitingRoom.setGameMode(4);
        waitingRoom.addUser("First to log!");
        assertThrows(ExistingNicknameException.class,
                () -> waitingRoom.addUser("First to log!"));
        assertEquals(1, waitingRoom.getWaitingUsers().size());
    }

    @Test
    @DisplayName("Checking addUser can discard logRequests from users with a nickname already used by an other player ONLINE")
    void addExistingPlayer() throws InvalidGameModeException, IOException, InvalidReadException, NoSuchPlayerException {
        WaitingRoom waitingRoom = new WaitingRoom();
        waitingRoom.setGameMode(4);
        Identity identityTest = new Identity("Player ONLINE");
        ArrayList<Identity> currentIdentities = new ArrayList<>();
        currentIdentities.add(identityTest);
        Game gameTest = new GameStub2(currentIdentities);
        waitingRoom.addIdentity(identityTest, gameTest);
        assertThrows(ExistingNicknameException.class,
                () -> waitingRoom.addUser("Player ONLINE"));
        assertEquals(1, waitingRoom.getWaitingUsers().size());
    }

    @Test
    @DisplayName("Checking addUser can accept logRequests from users had lost the connection during the game")
    void addDisconnectedPlayer() throws InvalidGameModeException, InvalidNicknameException, ExistingNicknameException, IOException, InvalidReadException, NoSuchPlayerException {
        WaitingRoom waitingRoom = new WaitingRoom();
        waitingRoom.setGameMode(4);
        Identity identityTest = new Identity("Player OFFLINE");
        identityTest.setOnline(false);
        ArrayList<Identity> currentIdentities = new ArrayList<>();
        currentIdentities.add(identityTest);
        Game gameTest = new GameStub2(currentIdentities);
        waitingRoom.addIdentity(identityTest, gameTest);
        waitingRoom.addUser("Player OFFLINE");
        assertTrue(identityTest.isOnline());
        assertEquals(0, waitingRoom.getWaitingUsers().size());
    }

    @Test
    @DisplayName("Checking addUser can handle null users")
    void addNullUser() throws InvalidGameModeException {
        WaitingRoom waitingRoom = new WaitingRoom();
        waitingRoom.setGameMode(4);
        assertThrows(NullPointerException.class,
                () -> waitingRoom.addUser(null));
        assertEquals(0, waitingRoom.getWaitingUsers().size());
    }
    @Test
    @DisplayName("Checking addUser can handle users without a nickname")
    void addAnonymousUser() throws InvalidGameModeException {
        WaitingRoom waitingRoom = new WaitingRoom();
        waitingRoom.setGameMode(4);
        assertThrows(NullPointerException.class,
                () -> waitingRoom.addUser(""));
        assertEquals(0, waitingRoom.getWaitingUsers().size());
    }


    // ------------- TEST REMOVE USER --------------------

    @Test
    @DisplayName("Checking removeUser can handle empty waitingRooms")
    void removeFromEmptyWaitingRoom() throws InvalidGameModeException {
        WaitingRoom waitingRoom = new WaitingRoom();
        waitingRoom.setGameMode(4);
        assertThrows(EmptyException.class,
                () -> waitingRoom.removeUser("No Nickname"));
        assertEquals(0, waitingRoom.getWaitingUsers().size());
    }

    @Test
    @DisplayName("Checking removeUser can handle not existing users")
    void removeNotExistingUser() throws InvalidGameModeException, InvalidNicknameException, ExistingNicknameException {
        WaitingRoom waitingRoom = new WaitingRoom();
        waitingRoom.setGameMode(4);
        waitingRoom.addUser("I am here");
        assertThrows(NoSuchUserException.class,
                () -> waitingRoom.removeUser("I am not here"));
        assertEquals(1, waitingRoom.getWaitingUsers().size());
    }

    @Test
    @DisplayName("Checking removeUser can work with correct inputs")
    void removeUserTest() throws InvalidGameModeException, InvalidNicknameException, ExistingNicknameException, EmptyException, NoSuchUserException {
        WaitingRoom waitingRoom = new WaitingRoom();
            waitingRoom.setGameMode(4);
            waitingRoom.addUser("First");
            waitingRoom.addUser("Second");
            waitingRoom.addUser("Third");
            waitingRoom.removeUser("Second");

        ArrayList<Identity> currentUsers =  waitingRoom.getWaitingUsers();
        assertEquals(2, currentUsers.size());
        assertEquals("First", currentUsers.get(0).getNickname());
        assertEquals("Third", currentUsers.get(1).getNickname());
    }


    // ------------- TEST SET GAME MODE --------------------

    @Test
    @DisplayName("Checking setGameMode works correctly with correct edge values")
    void setGameModeTest() throws InvalidGameModeException {
        WaitingRoom waitingRoom = new WaitingRoom();
        int gameMode;
        gameMode = 1;
        waitingRoom.setGameMode(gameMode);
        assertEquals(gameMode, waitingRoom.getGameMode());
        gameMode = 4;
        waitingRoom.setGameMode(gameMode);
        assertEquals(gameMode, waitingRoom.getGameMode());
    }

    @Test
    @DisplayName("Checking setGameMode can handle negative values")
    void negativeGameMode(){
        WaitingRoom waitingRoom = new WaitingRoom();
        int currentGameMode = waitingRoom.getGameMode();
        assertThrows(InvalidGameModeException.class,
                () -> waitingRoom.setGameMode(-1));
        assertEquals(currentGameMode, waitingRoom.getGameMode());
    }

    @Test
    @DisplayName("Checking setGameMode can handle 0 value")
    void zeroGameMode(){
        WaitingRoom waitingRoom = new WaitingRoom();
        int currentGameMode = waitingRoom.getGameMode();
        assertThrows(InvalidGameModeException.class,
                () -> waitingRoom.setGameMode(0));
        assertEquals(currentGameMode, waitingRoom.getGameMode());
    }

    @Test
    @DisplayName("Checking setGameMode can handle values greater than 4")
    void tooManyPLayersGameMode() throws InvalidGameModeException {
        WaitingRoom waitingRoom = new WaitingRoom();
        waitingRoom.setGameMode(2);
        int currentGameMode = waitingRoom.getGameMode();
        assertThrows(InvalidGameModeException.class,
                () -> waitingRoom.setGameMode(5));
        assertEquals(currentGameMode, waitingRoom.getGameMode());
    }

    @Test
    @DisplayName("Checking game mode can not be set to a value smaller than the number of users in the waiting room")
    void pushAwayUsers() throws InvalidGameModeException, InvalidNicknameException, ExistingNicknameException {
        WaitingRoom waitingRoom = new WaitingRoom();
        waitingRoom.setGameMode(3);
        waitingRoom.addUser("First");
        waitingRoom.addUser("Second");
        assertThrows(InvalidGameModeException.class,
                () -> waitingRoom.setGameMode(1));
        assertEquals(3, waitingRoom.getGameMode());
        assertEquals(2, waitingRoom.getWaitingUsers().size());
    }
}