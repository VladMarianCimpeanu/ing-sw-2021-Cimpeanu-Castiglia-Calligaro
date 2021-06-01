package it.polimi.ingsw.client.modelLight.GUI;

import it.polimi.ingsw.client.Clickable;
import it.polimi.ingsw.client.GUI;
import it.polimi.ingsw.client.MessageFromServer.ErrorMessage;
import it.polimi.ingsw.client.Resource;
import it.polimi.ingsw.client.modelLight.ActionToken.ActionTokenGUI;
import it.polimi.ingsw.client.modelLight.GameView;
import it.polimi.ingsw.client.panels.ActionPanel;
import it.polimi.ingsw.client.panels.BaseProdPanel;
import it.polimi.ingsw.client.panels.BuyPanel;
import it.polimi.ingsw.client.panels.DevProductionPanel;
import it.polimi.ingsw.client.panels.ActionPanel;
import it.polimi.ingsw.client.panels.DefaultPanel;
import it.polimi.ingsw.client.panels.FirstTurnPanel;
import it.polimi.ingsw.client.panels.WaitingRoomPanel;
import it.polimi.ingsw.client.panels.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static it.polimi.ingsw.client.GUI.getClient;
import static it.polimi.ingsw.client.GUI.getGamePanel;

public class GameGUI extends GameView {
    private String payPanel;
    public GameGUI() {
        market = new MarketGUI();
        cards = new DevelopmentCardsSetGUI();
        faithPath = new FaithPathGUI();
        payPanel = null;
    }

    public void setPayPanel(String payPanel) {
        this.payPanel = payPanel;
    }

    @Override
    public void setPlayers(ArrayList<String> nicknames) {
        if(nicknames.size() == 1) actionTokenView = new ActionTokenGUI();
        players = new TreeMap<>();
        //with TreeMap we can save the player order
        for(String nickname : nicknames){
            players.put(nickname, new PlayerGUI(nickname));
            faithPath.addPlayer(nickname);
        }
    }

    @Override
    public void dumpMessage(String content) {
        GUI.print(content);
    }

    @Override
    public void displayResourcesToPay(Map<Resource, Integer> resources) {
        //add a concept of state?
        //how can i know if i am in a production state or buy state
        if(payPanel.equals("buy")) {
            GUI.getGamePanel().setActionPanel(new BuyPanel());
            ((DevelopmentCardDecksGUI)GUI.getClient().getGameView().getPlayer(getClient().getNickname()).getDecks()).setToReplaceable();
        }
        else if(payPanel.equals("production")) {
            GUI.getGamePanel().setActionPanel(new DevProductionPanel());
        }else if(payPanel.equals("base")){

        }
        payPanel = "";
        setResBuffer(resources);
        ((DepotGUI) getPlayer(GUI.getClient().getNickname()).getDepot()).setStrategyTake();
        ((LeaderCardSetGUI) getPlayer(GUI.getClient().getNickname()).getLeaderCards()).setLeadersToBuyStrategy();
        ActionPanel panel = GUI.getGamePanel().getActionPanel();
        panel.repaint();
    }

    @Override
    public void notifyJoin(String content, int size, ArrayList<String> players) {
        GUI.goToWaitingRoom(size, players);
    }


    @Override
    public void displayError(ErrorMessage error) {
        if(error == ErrorMessage.invalidNickname || error == ErrorMessage.usedNickname){
            GUI.getLoginPanel().setError(error.getCaption());
        }
        else {
            GUI.getGamePanel().getActionPanel().displayError(error);
        }
    }

    @Override
    public void newWaitingPlayer(String player) {
        GUI.getWaitingRoomPanel().addNickname(player);
    }

    @Override
    public void crashedWaitingPlayer(String player) {
        GUI.getWaitingRoomPanel().removeNickname(player);
    }

    @Override
    public void startGame() {
        GUI.goToGame();
    }

    @Override
    public void requireMode() {
        GUI.getLoginPanel().requireMode();
    }

    @Override
    public void chooseResources(int position, int number) {
        FirstTurnPanel panel = (FirstTurnPanel) GUI.getGamePanel().getActionPanel();
        panel.selectRes(position, number);
    }

    @Override
    public void firstTurnEnded() {
        GUI.getGamePanel().setActionPanel(new DefaultPanel());
        ((LeaderCardSetGUI)GUI.getClient().getGameView().getPlayer(GUI.getClient().getNickname()).getLeaderCards()).setLeadersToDefaultStrategy();
        GUI.getGamePanel().revalidate();
    }

    @Override
    public void lastProduced(Map<Resource, Integer> resources, String player) {
        ((DepotGUI)GUI.getClient().getGameView().getPlayer(GUI.getClient().getNickname()).getDepot()).setStrategyMove();
        ((LeaderCardSetGUI)GUI.getClient().getGameView().getPlayer(GUI.getClient().getNickname()).getLeaderCards()).setLeadersToDefaultStrategy();
        //GUI.getGamePanel().getActionPanel().getClickable().clear(); //not so efficient(remain listening)
        GUI.getGamePanel().setActionPanel(new DefaultPanel());
        GUI.getGamePanel().repaint();
    }

    @Override
    public void changeTurn(String player) {
        if (player.equals(GUI.getClient().getNickname())) {
            GUI.getGamePanel().unlockGameBoard(true);
            dumpMessage("It's your turn!");
        } else {
            dumpMessage("It's " + player + "'s turn!");
            GUI.getGamePanel().unlockGameBoard(false);
        }
        GUI.getGamePanel().setActionPanel(new DefaultPanel());
    }

    @Override
    public void startBaseProd() {
        GUI.getGamePanel().setActionPanel(new BaseProdPanel());
        GUI.getGamePanel().repaint();
    }

    @Override
    public void handleCrash() {
        GUI.getClient().stopTheGame();
        GUI.closeGame();
    }

    @Override
    public void updateResourcesFromMarket(ArrayList<Resource> resources) {
        ((LeaderCardSetGUI)players.get(GUI.getClient().getNickname()).getLeaderCards()).setLeadersToDefaultStrategy();
        GUI.getGamePanel().removeAction((LeaderCardSetGUI)getPlayer(GUI.getClient().getNickname()).getLeaderCards());
        if(!((MarketPanel)GUI.getGamePanel().getActionPanel()).isTakingResources())
            ((MarketPanel)GUI.getGamePanel().getActionPanel()).setToSelectResources();
        if(resources.isEmpty()) {
            GUI.getGamePanel().setActionPanel(new DefaultPanel());
            GUI.getGamePanel().unlockGameBoard(true);
        }
        else {
            setResBuffer(resources);
            ((MarketPanel)GUI.getGamePanel().getActionPanel()).updateRemainingResources();
        }
    }

    @Override
    public void updatedUsedStrategies(int whiteMarbles, int strategy) {
        if(strategy == 0) {
            GUI.getGamePanel().unlockGameBoard(false);
            GUI.getGamePanel().addAction((LeaderCardSetGUI)getPlayer(GUI.getClient().getNickname()).getLeaderCards());
            GUI.getGamePanel().setActionPanel(new MarketPanel());
            if(whiteMarbles != 0) {
                ((LeaderCardSetGUI) players.get(GUI.getClient().getNickname()).getLeaderCards()).setLeadersToMarketStrategy();
                ((MarketPanel)GUI.getGamePanel().getActionPanel()).updateStrategies(whiteMarbles, strategy);
            }
        }
        if(whiteMarbles == 0 ) {
            if(strategy != 0) ((MarketPanel)GUI.getGamePanel().getActionPanel()).updateStrategies(whiteMarbles, strategy);
            ((MarketPanel)GUI.getGamePanel().getActionPanel()).setToSelectResources();
            ((LeaderCardSetGUI)players.get(GUI.getClient().getNickname()).getLeaderCards()).setLeadersToDefaultStrategy();
        }
        else if(strategy != 0)((MarketPanel)GUI.getGamePanel().getActionPanel()).updateStrategies(whiteMarbles, strategy);
    }

    @Override
    public void rejoinGame(){
        super.rejoinGame();
        GUI.getGamePanel().setupRejoin();
    }
}
