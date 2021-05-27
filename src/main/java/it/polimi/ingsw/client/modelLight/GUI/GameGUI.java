package it.polimi.ingsw.client.modelLight.GUI;

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

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static it.polimi.ingsw.client.GUI.getGamePanel;

public class GameGUI extends GameView {
    public GameGUI() {
        market = new MarketGUI();
        cards = new DevelopmentCardsSetGUI();
        faithPath = new FaithPathGUI();
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
        ActionPanel panel = GUI.getGamePanel().getActionPanel();
        //add a concept of state?
        //how can i know if i am in a production state or buy state
        ((DepotGUI)getPlayer(GUI.getClient().getNickname()).getDepot()).setStrategyTake();
        setResBuffer(resources);
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
        GUI.getGamePanel().revalidate();
    }
}
