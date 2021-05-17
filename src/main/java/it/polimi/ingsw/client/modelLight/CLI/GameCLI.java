package it.polimi.ingsw.client.modelLight.CLI;


import it.polimi.ingsw.client.modelLight.FaithPathView;
import it.polimi.ingsw.client.modelLight.GameView;

import java.util.ArrayList;
import java.util.TreeMap;

public class GameCLI extends GameView {
    public GameCLI(ArrayList<String> nicknames) {
        players = new TreeMap<>();
        //with TreeMap we can save the player order
        for(String nickname : nicknames) players.put(nickname, new PlayerCLI(nickname));
        market = new MarketCLI();
        faithPath = new FaithPathCLI();
        cards = new DevelopmentCardsSetCLI();
        printPlayers(nicknames);
    }

    private void printPlayers(ArrayList<String> nicknames){
        System.out.println("Game Started.");
        System.out.print("Players: ");
        boolean firstIteration = true;
        for(String nick: nicknames){
            if(!firstIteration) System.out.print(", ");
            System.out.print(nick);
            firstIteration = false;
        }
        System.out.println(".");
    }
}
