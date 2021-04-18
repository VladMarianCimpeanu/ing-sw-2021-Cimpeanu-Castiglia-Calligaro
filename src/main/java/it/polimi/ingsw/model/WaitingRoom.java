package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class manages the phase before the game creation where the players queue up and wait for the beginning of the concrete game
 * The object instantiated from this class is unique during all the execution of the program
 * The first user joining the waiting room can set the game mode where he can set the number of players of the next game, where he would join too
 * While users join the waiting room, their identity will be added to the list of users that are waiting for the beginning of the game
 * When the size of the waiting users list will be equal to the game mode set by the first, the game can be created and let it start, clearing the list of waiting users
 */

public class WaitingRoom {
    private int gameMode;
    private final Map<Identity, Game> identities;
    private final ArrayList<Identity> waitingUsers;

    public WaitingRoom(){
        identities = new HashMap<Identity, Game>();
        waitingUsers = new ArrayList<Identity>();
        gameMode = 1;
    }

    /**
     * It adds a new player to the waiting room if its nickname isn't used by anyone that is inside the waitingRoom or is playing any game.
     * If the number of players matches the number set by the host, then it starts a new game.
     * @param nickname used by the user to register to the waiting room.
     * @throws ExistingNicknameException
     * @throws NullPointerException
     */
    public void addUser(String nickname) throws ExistingNicknameException, NullPointerException, InvalidStepsException, NoSuchPlayerException, IOException, InvalidReadException {
        if(nickname == null || nickname.equals("")) throw new NullPointerException();
        for(Identity id : waitingUsers)
            if(id.getNickname().equals(nickname)) throw new ExistingNicknameException();
        for(Identity id : identities.keySet())
            if(id.getNickname().equals(nickname))
                if(id.isOnline())
                    throw new ExistingNicknameException();
                else {
                    id.setOnline(true);
                    return;
                }
        waitingUsers.add(new Identity(nickname));
        if(gameMode == waitingUsers.size()) createGame();
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
     * @param gameMode specified game mode for the next game.
     * @throws InvalidGameModeException
     */
    public void setGameMode(int gameMode) throws InvalidGameModeException {
        if(gameMode < 1 || gameMode > 4 || gameMode < waitingUsers.size()) throw new InvalidGameModeException();
        this.gameMode = gameMode;
    }

    /**
     * it creates a game with the users in the waiting room and the settings chosen by the host.
     */
    private void createGame() throws InvalidStepsException, NoSuchPlayerException, IOException, InvalidReadException {
        Game game;
        if(gameMode == 1) game = new Singleplayer(waitingUsers);
        else game = new Multiplayer(waitingUsers);
        for (Identity id: waitingUsers)
            addIdentity(id, game);
        waitingUsers.clear();
    }

    public ArrayList<Identity> getWaitingUsers() {
        return waitingUsers;
    }

    public Map<Identity, Game> getIdentities() {
        return identities;
    }

    public void addIdentity(Identity identity, Game game){
        if(!identities.containsKey(identity))
            identities.put(identity, game);
    }
}
