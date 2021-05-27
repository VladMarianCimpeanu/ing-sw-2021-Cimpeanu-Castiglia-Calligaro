package it.polimi.ingsw.client;

import it.polimi.ingsw.client.MessageToServer.KeepLeaderCard;
import it.polimi.ingsw.client.MessageToServer.MessageToServer;
import it.polimi.ingsw.client.panels.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GUI {
    private static JFrame jFrame;
    private static Client client;
    private static GamePanel gamePanel = new GamePanel();
    private static LoginPanel loginPanel;
    private static WaitingRoomPanel waitingRoomPanel;
    private static int leaderToKeep = 0;

    public static void setClient(Client client) {
        GUI.client = client;
    }

    public static void sendMessage(MessageToServer message){
        client.send(message);
    }

    public static void start(){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        System.out.println("Created GUI on EDT? "+
                SwingUtilities.isEventDispatchThread());
        jFrame = new JFrame("Maestri del rinascimento");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginPanel = new LoginPanel();
        jFrame.add(loginPanel);
        jFrame.setSize(400, 190);

        jFrame.setVisible(true);
    }

    public static Client getClient() {
        return client;
    }

    public static GamePanel getGamePanel() {
        return gamePanel;
    }

    public static LoginPanel getLoginPanel(){
        return loginPanel;
    }

    public static WaitingRoomPanel getWaitingRoomPanel(){
        return waitingRoomPanel;
    }

    public static void goToWaitingRoom(int size, ArrayList<String> players){
        jFrame.remove(loginPanel);
        waitingRoomPanel = new WaitingRoomPanel(size, players);
        jFrame.add(waitingRoomPanel);
        jFrame.setVisible(false);
        jFrame.setVisible(true);
    }

    public static void goToGame(){
        jFrame.remove(waitingRoomPanel);
        jFrame.add(gamePanel);
        jFrame.setSize(1280,720);
        jFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        gamePanel.setPlayerWatched(GUI.getClient().getNickname());
        gamePanel.addOtherPlayersPanel();

        jFrame.setVisible(false);
        jFrame.setVisible(true);
    }

    public static void keepLeader(int id){
        if(leaderToKeep == 0){
            leaderToKeep = id;
        }else{
            sendMessage(new KeepLeaderCard(leaderToKeep, id, client.getNickname()));
            leaderToKeep = 0;
        }
    }

    public static void print(String string){
        gamePanel.getScrollPanel().getMessagesPanel().addMessage(string);
    }
}
