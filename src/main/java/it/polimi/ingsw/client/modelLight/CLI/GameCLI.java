package it.polimi.ingsw.client.modelLight.CLI;

import it.polimi.ingsw.client.Color;
import it.polimi.ingsw.client.MessageFromServer.ErrorMessage;
import it.polimi.ingsw.client.Resource;
import it.polimi.ingsw.client.modelLight.ActionToken.ActionTokenCLI;
import it.polimi.ingsw.client.modelLight.GameView;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class GameCLI extends GameView {
    public GameCLI() {
        market = new MarketCLI();
        cards = new DevelopmentCardsSetCLI();
        faithPath = new FaithPathCLI();
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

    @Override
    public void displayResourcesToPay(Map<Resource, Integer> resources) {
        String content = "You have to pay the following resources: ";
        for(Resource res: resources.keySet()){
            content += resources.get(res).toString() + " " + res.escape() + Color.RESET + " ";
        }
        dumpMessage(content);
    }

    @Override
    public void notifyJoin(String content) {
        System.out.println(content);
    }

    @Override
    public void displayError(ErrorMessage error) {
        System.out.println(error.getCaption());
    }

    @Override
    public void newWaitingPlayer(String player) {
        dumpMessage(player + " joined the game.");
    }

    @Override
    public void crashedWaitingPlayer(String player) {
        dumpMessage(player + " has left the game.");
    }

    @Override
    public void startGame() {
        dumpMessage("Game started!");
    }

    @Override
    public void requireMode() {
        dumpMessage("There are no waiting rooms available: you are the host. Choose number of players");
    }

}
