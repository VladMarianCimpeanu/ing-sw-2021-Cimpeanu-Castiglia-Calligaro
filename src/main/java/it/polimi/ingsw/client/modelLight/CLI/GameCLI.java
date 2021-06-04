package it.polimi.ingsw.client.modelLight.CLI;

import it.polimi.ingsw.client.Color;
import it.polimi.ingsw.client.MessageFromServer.ErrorMessage;
import it.polimi.ingsw.client.Resource;
import it.polimi.ingsw.client.modelLight.ActionToken.ActionTokenCLI;
import it.polimi.ingsw.client.modelLight.GameView;

import java.util.*;

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
    public void notifyJoin(String content, int size, ArrayList<String> players) {
        if(content.equals("created")){
            System.out.print("Created a new waiting room. ");
        }else{
            System.out.print("Joined a waiting room. ");
        }
        System.out.println("Size: " + size);
        System.out.println("Players in waitingRoom: " + players);
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

    @Override
    public void chooseResources(int position, int number) {
        System.out.println("You are in position: " + position + " | Choose " + number +  " resources");
    }

    @Override
    public void firstTurnEnded() {
        System.out.println("You have finished your first turn");
    }

    @Override
    public void lastProduced(Map<Resource, Integer> resources, String player) {
        System.out.println(player + " has produced these resources " + resources.toString() + "\nin this turn.");
    }

    @Override
    public void changeTurn(String player) {
        dumpMessage("It's " + player + "'s turn!");
    }

    @Override
    public void startBaseProd() {
        System.out.println("Which resources do you want to use for base crafting?");
    }

    @Override
    public void startExtraProd() {
        System.out.println("Which resource do you want to craft?");
    }

    @Override
    public void handleCrash() {
        System.out.println("Something strange with the connection happened. Quit the game.");
    }

    @Override
    public void endGame(Map<String, Integer> ranks, Map<String, Integer> resources) {
        ArrayList<String> order = orderPlayers(new HashMap<>(ranks), resources);
        if(order.contains("blackCross")){
            if(order.get(0).equals("blackCross"))
                System.out.println("You're a looser");
            else
                dumpMessage("You've won and your score is: "+ranks.get(order.get(0)));
        }else{
            for(String name: order)
                dumpMessage((order.indexOf(name)+1)+")"+name+" "+ranks.get(name));
        }
    }

    @Override
    public void updateResourcesFromMarket(ArrayList<Resource> resources) {
        String resourcesToDump = "";
        for(Resource res: resources){
            resourcesToDump += " " + (res.toString().toLowerCase(Locale.ROOT));
        }
        if(!resources.isEmpty()) dumpMessage("Resources to redeem left are:" +  resourcesToDump);
    }

    @Override
    public void updatedUsedStrategies(int whiteMarbles, int strategies) {
        if(whiteMarbles == 0) dumpMessage("There are no more white marbles to convert");
        else dumpMessage("There are " + whiteMarbles + " to convert.");
    }

}
