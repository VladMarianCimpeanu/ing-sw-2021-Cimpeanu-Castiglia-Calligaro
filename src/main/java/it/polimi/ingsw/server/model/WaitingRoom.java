package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.MultiEchoServer;
import it.polimi.ingsw.server.model.exceptions.*;

import java.util.ArrayList;

/**
 * This class manages the phase before the game creation where the players queue up and wait for the beginning of the concrete game
 * The object instantiated from this class is unique during all the execution of the program
 * The first user joining the waiting room can set the game mode where he can set the number of players of the next game, where he would join too
 * While users join the waiting room, their identity will be added to the list of users that are waiting for the beginning of the game
 * When the size of the waiting users list will be equal to the game mode set by the first, the game can be created and let it start, clearing the list of waiting users
 */

public class WaitingRoom {
    private int gameMode = 4;
    private ArrayList<Identity> waitingUsers;

    public WaitingRoom(int gameMode){
        waitingUsers = new ArrayList<>();
        this.gameMode = gameMode;
    }

    /**
     * It adds a new player to the waiting room if its nickname isn't used by anyone that is inside the waitingRoom or is playing any game.
     * If the number of players matches the number set by the host, then it starts a new game.
     * @param nickname used by the user to register to the waiting room.
     * @throws NullPointerException
     */
    public void addUser(String nickname) throws NullPointerException {
        if(nickname == null || nickname.equals("")) throw new NullPointerException();
        waitingUsers.add(new Identity(nickname));
        if(gameMode <= waitingUsers.size()) {
            new Controller(waitingUsers);
            MultiEchoServer.removeWaitingRoom(this);
        }
    }

    /**
     * it removes the user if he lost the connection when he is inside the waiting room.
     * @param nickname used by the user to register to the waiting room.
     * @throws NoSuchUserException
     * @throws NullPointerException
     */

    public void removeUser(String nickname) throws NoSuchUserException, NullPointerException, EmptyException {
        if(nickname == null) throw new NullPointerException();
        if(waitingUsers.isEmpty()) throw new EmptyException();
        for(Identity id : waitingUsers)
            if(id.getNickname().equals(nickname)) {
                waitingUsers.remove(id);
                return;
            }
        throw new NoSuchUserException();
    }

    public int getGameMode() {
        return gameMode;
    }

    /**
     * it sets the game mode chosen by the host (the first user to register to the waiting room)
     * game modes : 1 == single player | 2 or 3 or 4 == number of players allowed to play at this game (multiplayer).
     * @param mode specified game mode for the next game.
     * @throws InvalidGameModeException
     */
    public void setGameMode(int mode) throws InvalidGameModeException {
        if(mode < 1 || mode > 4 || mode < waitingUsers.size()) throw new InvalidGameModeException();
        gameMode = mode;
    }

    public ArrayList<Identity> getWaitingUsers() {
        return new ArrayList<>(waitingUsers);
    }

    public boolean contains(String nickname){
        for(Identity i: waitingUsers)
            if(i.getNickname().equals(nickname)) return true;
        return false;
    }
}
