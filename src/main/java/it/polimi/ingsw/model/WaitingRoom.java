package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.ExistingNicknameException;
import it.polimi.ingsw.model.exceptions.InvalidGameModeException;
import it.polimi.ingsw.model.exceptions.InvalidNicknameException;
import it.polimi.ingsw.model.exceptions.NoSuchUserException;
import it.polimi.ingsw.model.exceptions.EmptyException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WaitingRoom {
    private int gameMode;
    private Map<Identity, Game> Identities;
    private ArrayList<Identity> waitingUsers;

    public WaitingRoom(){
        Identities = new HashMap<Identity, Game>();
        waitingUsers = new ArrayList<Identity>();
        gameMode = 4;
    }

    /**
     * It adds a new player to the waiting room if its nickname isn't used by anyone that is inside the waitingRoom or is playing any game.
     * If the number of players matches the number set by the host, then it starts a new game.
     * @param nickname used by the user to register to the waiting room.
     * @throws InvalidNicknameException
     * @throws ExistingNicknameException
     * @throws NullPointerException
     */
    public void addUser(String nickname) throws InvalidNicknameException, ExistingNicknameException, NullPointerException {
        // TO DO
    }

    /**
     * it removes the user if he lost the connection when he is inside the waiting room.
     * @param nickname used by the user to register to the waiting room.
     * @throws NoSuchUserException
     * @throws NullPointerException
     */
    public void removeUser(String nickname) throws NoSuchUserException, NullPointerException, EmptyException{
        // TO DO
    }

    public int getGameMode() {
        return gameMode;
    }

    /**
     * it sets the game mode chosen by the host (the first user to register to the waiting room)
     * game modes : 1 == single player | 2 or 3 or 4 == number of players allowed to play at this game (multiplayer).
     * @param gameMode specified game mode for the next game.
     * @throws InvalidGameModeException
     */
    public void setGameMode(int gameMode) throws InvalidGameModeException {

    }

    /**
     * it creates a game with the users in the waiting room and the settings chosen by the host.
     */
    private void createGame(){

    }

    public ArrayList<Identity> getWaitingUsers() {
        return null; // TO DO
    }

    public Map<Identity, Game> getIdentities() {
        return Identities;
    }

    public void addIdentity(Identity identity, Game game){

    }
}
