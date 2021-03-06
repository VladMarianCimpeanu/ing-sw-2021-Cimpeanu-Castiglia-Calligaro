package it.polimi.ingsw.client.modelLight.GUI;

import it.polimi.ingsw.client.Clickable;
import it.polimi.ingsw.client.GUI;
import it.polimi.ingsw.client.MessageFromServer.ErrorMessage;
import it.polimi.ingsw.client.Resource;
import it.polimi.ingsw.client.modelLight.ActionToken.ActionTokenGUI;
import it.polimi.ingsw.client.modelLight.GameView;
import it.polimi.ingsw.client.modelLight.PlayerView;
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
    private String prodPanel;

    public GameGUI() {
        market = new MarketGUI();
        cards = new DevelopmentCardsSetGUI();
        faithPath = new FaithPathGUI();
        payPanel = "";
        prodPanel = "";
    }

    public void setPayPanel(String payPanel) {
        this.payPanel = payPanel;
    }

    public void setProdPanel(String prodPanel) {
        this.prodPanel = prodPanel;
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
        if(payPanel.equals("buy")) {
            PlayerView player = GUI.getClient().getGameView().getPlayer(GUI.getClient().getNickname());
            GUI.getGamePanel().setActionPanel(new BuyPanel());
            ((DevelopmentCardDecksGUI)GUI.getClient().getGameView().getPlayer(getClient().getNickname()).getDecks()).setToReplaceable();
        }else if(payPanel.equals("production")) {
            PlayerView player = GUI.getClient().getGameView().getPlayer(GUI.getClient().getNickname());
            GUI.getGamePanel().setActionPanel(new DevProductionPanel());
        }else if(payPanel.equals("base")){
            ((BaseProdPanel)GUI.getGamePanel().getActionPanel()).setPhase(1);
        }else if(payPanel.equals("extra")){
            ((ExtraProdPanel)GUI.getGamePanel().getActionPanel()).setPhase(1);
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
        WaitingRoomPanel panel =  GUI.getWaitingRoomPanel();
        if(panel != null)
            panel.removeNickname(player);
        else
            GUI.print(player + " has left the game.");
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
        ActionPanel panel = GUI.getGamePanel().getActionPanel();
        ((DepotGUI)players.get(GUI.getClient().getNickname()).getDepot()).setStrategyFirstTurn(); //TODO ok?
        if(!panel.isFirstTurn()) GUI.getGamePanel().setActionPanel(new FirstTurnPanel());
        ((FirstTurnPanel)GUI.getGamePanel().getActionPanel()).selectRes(position, number);
    }

    @Override
    public void firstTurnEnded() {
        GUI.getGamePanel().setActionPanel(new DefaultPanel());
        ((LeaderCardSetGUI)GUI.getClient().getGameView().getPlayer(GUI.getClient().getNickname()).getLeaderCards()).setLeadersToDefaultStrategy();
        GUI.getGamePanel().revalidate();
    }

    @Override
    public void lastProduced(Map<Resource, Integer> resources, String player) {
        if(!player.equals(GUI.getClient().getNickname())) return;
        ((DepotGUI)GUI.getClient().getGameView().getPlayer(player).getDepot()).setStrategyMove();
        ((LeaderCardSetGUI)GUI.getClient().getGameView().getPlayer(player).getLeaderCards()).setLeadersToDefaultStrategy();
        PlayerView p = GUI.getClient().getGameView().getPlayer(GUI.getClient().getNickname());
        GUI.getGamePanel().removeAction((Clickable) p.getLeaderCards());
        GUI.getGamePanel().removeAction((Clickable) p.getDepot());
        GUI.getGamePanel().removeAction((Clickable) p.getStrongbox());
        GUI.getGamePanel().unlockGameBoard(true);
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
        PlayerView player = GUI.getClient().getGameView().getPlayer(GUI.getClient().getNickname());
        ((DepotGUI)player.getDepot()).setStrategyTake();
        ((LeaderCardSetGUI)player.getLeaderCards()).setLeadersToBuyStrategy();
        GUI.getGamePanel().setActionPanel(new BaseProdPanel());
        GUI.getGamePanel().repaint();
        prodPanel = "";
    }

    @Override
    public void startExtraProd() {
        if(prodPanel.equals("extra")){
            PlayerView player = GUI.getClient().getGameView().getPlayer(GUI.getClient().getNickname());
            ((DepotGUI)player.getDepot()).setStrategyTake();
            ((LeaderCardSetGUI)player.getLeaderCards()).setLeadersToBuyStrategy();
            GUI.getGamePanel().setActionPanel(new ExtraProdPanel());
            GUI.getGamePanel().repaint();
            prodPanel = "";
        }
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
        ((LeaderCardSetGUI) players.get(GUI.getClient().getNickname()).getLeaderCards()).setSlotsClickable();
        if(!((MarketPanel)GUI.getGamePanel().getActionPanel()).isTakingResources()){
            ((MarketPanel)GUI.getGamePanel().getActionPanel()).setToSelectResources();
            GUI.getGamePanel().addAction((DepotGUI)players.get(GUI.getClient().getNickname()).getDepot());
        }

        if(resources.isEmpty()) {
            GUI.getGamePanel().setActionPanel(new DefaultPanel());
            GUI.getGamePanel().unlockGameBoard(true);
            GUI.getGamePanel().removeAllActions();
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
            GUI.getGamePanel().addAction((DepotGUI)players.get(GUI.getClient().getNickname()).getDepot());
        }
        else if(strategy != 0)((MarketPanel)GUI.getGamePanel().getActionPanel()).updateStrategies(whiteMarbles, strategy);
    }

    @Override
    public void rejoinGame(){
        super.rejoinGame();
        GUI.getGamePanel().setupRejoin();
    }

    @Override
    public void endGame(Map<String, Integer> ranks, Map<String, Integer> resources) {
        ArrayList<String> order = orderPlayers(new HashMap<>(ranks), resources);
        GUI.showPoints(order, ranks);
    }
}
