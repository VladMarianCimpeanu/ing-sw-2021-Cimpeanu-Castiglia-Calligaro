package it.polimi.ingsw.client.modelLight.CLI;

import it.polimi.ingsw.client.modelLight.ActionToken.ActionTokenCLI;
import it.polimi.ingsw.client.modelLight.GameView;

import java.util.ArrayList;
import java.util.TreeMap;

public class GameCLI extends GameView {
    public GameCLI() {
        market = new MarketCLI();
        faithPath = new FaithPathCLI();
        cards = new DevelopmentCardsSetCLI();
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

    public void setPlayers(ArrayList<String> nicknames){
        if(nicknames.size() == 1) actionTokenView = new ActionTokenCLI();
        players = new TreeMap<>();
        //with TreeMap we can save the player order
        for(String nickname : nicknames) players.put(nickname, new PlayerCLI(nickname));
        printPlayers(nicknames);
    }

    @Override
    public void dumpMessage(String content) {
        System.out.println(content);
    }
}
