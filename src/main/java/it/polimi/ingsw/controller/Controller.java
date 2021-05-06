package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.states.TurnState;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.exceptions.InvalidReadException;
import it.polimi.ingsw.model.exceptions.InvalidStepsException;
import it.polimi.ingsw.model.exceptions.NoSuchPlayerException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import static java.util.Collections.shuffle;

public class Controller {
    private Game game;
    private String currentUser;
    private boolean isFirstTurn;
    private ArrayList<String> waitingForLeaderCards;
    private Map<String, Integer> firstTurnResources;
    private Map<String, EchoServerClientHandler> nicknames;
    private Map<String, Player> players;
    //nicknames in order of turns
    private ArrayList<String> turns;
    private TurnState currentState;

    public Controller(ArrayList<Identity> users){
        nicknames = new HashMap<>();
        players = new HashMap<>();
        turns = new ArrayList<>();
        waitingForLeaderCards = new ArrayList<>();
        firstTurnResources = new HashMap<>();
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

        waitingForLeaderCards = new ArrayList<>(turns);
        for(int i = 2; i <= turns.size(); i++)
            firstTurnResources.put(turns.get(i-1), i/2);
    }

    //TODO: CRASH management
    public void rejoinClient(EchoServerClientHandler client, String nickname) {

    }

    public Identity getIdentity(String nickname){
        return players.get(nickname).getIdentity();
    }

    public ArrayList<Player> getPlayers() {
        return new ArrayList<> (players.values());
    }

    public void sendMessage(String command, ArrayList<String> params){
        nicknames.get(currentUser).send(command, params);
    }

    public void sendSimple(String c, String p){
        nicknames.get(currentUser).sendSimple(c, p);
    }

    public void sendError(String e) {
        nicknames.get(currentUser).sendError(e);
    }

    public void setCurrentState(TurnState currentState) {
        this.currentState = currentState;
    }

    public void nextTurn(){

    }
}