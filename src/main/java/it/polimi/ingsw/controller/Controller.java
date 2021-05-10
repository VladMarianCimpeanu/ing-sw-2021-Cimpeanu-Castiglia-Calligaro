package it.polimi.ingsw.controller;

import it.polimi.ingsw.MessageToClient.MessageToClient;
import it.polimi.ingsw.controller.states.TurnState;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.exceptions.InvalidReadException;
import it.polimi.ingsw.model.exceptions.InvalidStepsException;
import it.polimi.ingsw.model.exceptions.NoSuchPlayerException;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import static java.util.Collections.shuffle;

public class Controller {
    private Game game;
    private String currentUser;
    private boolean isFirstTurn;

    private Map<String, EchoServerClientHandler> nicknames;
    private Map<String, Player> players;
    //nicknames in order of turns
    private ArrayList<String> turns;
    private TurnState currentState;

    public Controller(ArrayList<Identity> users){
        nicknames = new HashMap<>();
        players = new HashMap<>();
        turns = new ArrayList<>();

        System.out.print("A new Game has started. Players: ");
        ArrayList<Identity> identities = new ArrayList<>(users);
        shuffle(identities);
        try{
            if(identities.size() == 1) game = new Singleplayer(identities);
            else game = new Multiplayer(identities);
        }catch(InvalidReadException | IOException | NoSuchPlayerException | InvalidStepsException e){
            e.printStackTrace();
        }
        for(Identity i: identities){
            String nick = i.getNickname();
            EchoServerClientHandler client = MultiEchoServer.getClient(nick);
            client.setController(this);
            nicknames.put(nick, client);
            turns.add(nick);
        }
        System.out.println("Shuffling players");
        System.out.println("Player order: " + turns);
        for(Player p: game.getPlayers())
            players.put(p.getNickName(),p);
        new VirtualView(this);
    }

    //TODO: CRASH management
    public void rejoinClient(EchoServerClientHandler client, String nickname) {

    }


    public ArrayList<String> getTurns() {
        return turns;
    }

    public Game getGame() {
        return game;
    }

    public Player getPlayer(String nickname){return players.get(nickname);}
    public Player getCurrentPlayer(){
        return players.get(currentUser);
    }

    public Identity getIdentity(String nickname){
        return players.get(nickname).getIdentity();
    }

    public ArrayList<Player> getPlayers() {
        return new ArrayList<> (players.values());
    }

    /**
     * Sends a message to all the players of the game.
     * @param message specific message to send.
     */
    public void sendBroadcast(MessageToClient message){
        for(EchoServerClientHandler client : nicknames.values()) {
            client.send(message);
        }
    }

    /**
     * sends a message to the current player.
     * @param message  specific message to send.
     */
    public void sendMessage(MessageToClient message){
        nicknames.get(currentUser).send(message);
    }
    @Deprecated
    public void sendSimple(String command, String params){

    }
    /**
     * sends an error to the current player.
     * @param e  specific error to send.
     */
    public void sendError(String e) {
        nicknames.get(currentUser).sendError(e);
    }

    public void setCurrentState(TurnState currentState) {
        this.currentState = currentState;
    }

    public void nextTurn(){
        if(isAnyoneOnline()) {
            //endGame: nobody is online
        }
        int pos = turns.indexOf(currentUser);
        pos = (pos+1)%turns.size();
        currentUser = turns.get(pos);
        if(!getCurrentPlayer().isOnline()) {
            nextTurn();
            //possible problems if everyone in the game is disconnected
            return;
        }
        try {
            nicknames.get(currentUser).setSocketTimeOut(30*1000);
        } catch (SocketException e) {
            getCurrentPlayer().getIdentity().setOnline(false);
        }
        for(String user: turns) {
            if (user.equals(currentUser)) continue;
            try {
                nicknames.get(user).setSocketTimeOut(0);
            } catch (SocketException e) {
                players.get(user).getIdentity().setOnline(false);
            }
        }
    }

    public void startGame(){
        //TODO
    }

    private boolean isAnyoneOnline(){
        for(String p: turns)
            if(players.get(p).isOnline()) return true;
        return false;
    }
}