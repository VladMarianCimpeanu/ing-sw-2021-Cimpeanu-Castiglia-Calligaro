package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Identity;
import it.polimi.ingsw.model.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import static java.util.Collections.shuffle;

public class Controller {
    private Game game;
    private String currentUser;
    private Map<String, EchoServerClientHandler> nicknames;
    private Map<String, Player> players;
    //nicknames in order of turns
    private ArrayList<String> turns;

    public Controller(Game game, ArrayList<Identity> users){
        nicknames = new HashMap<>();
        players = new HashMap<>();
        turns = new ArrayList<>();
        this.game = game;
        System.out.print("A new Game has started. Players: ");
        if(users != null){
            for(Identity u: users){
                String nick = u.getNickname();
                EchoServerClientHandler client = MultiEchoServer.getClient(nick);
                client.setController(this);
                nicknames.put(nick, client);

                turns.add(nick);
            }
        }
        System.out.println(turns);
        for(Player p: game.getPlayers()){
            players.put(p.getNickName(),p);
        }
        shuffle(turns);
        System.out.println("Shuffling players");
        System.out.println("Player order: " + turns);
    }

    //TODO: CRASH management
    public void addClient(EchoServerClientHandler client, String nickname) {

    }

    public void receive(EchoServerClientHandler sender, Message message){

    }

    public Identity getIdentity(String nickname){
        return players.get(nickname).getIdentity();
    }

    public ArrayList<Player> getPlayers() {
        return new ArrayList<> (players.values());
    }
}
