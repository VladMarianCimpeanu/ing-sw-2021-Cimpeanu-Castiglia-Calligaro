package it.polimi.ingsw.client;

import it.polimi.ingsw.client.MessageToServer.MessageToServer;
import it.polimi.ingsw.server.model.WaitingRoom;

import javax.swing.*;

public class GUI {
    private static Client client;
    private static final GamePanel gamePanel = new GamePanel();

    private static LoginPanel loginPanel;

    private static WaitingRoomPanel waitingRoomPanel;

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
        JFrame f = new JFrame("Maestri del rinascimento");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(gamePanel);
        f.setSize(1280,720);

        f.setExtendedState(JFrame.MAXIMIZED_BOTH);

        f.setVisible(true);
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

    public static void goToWaitingRoom(){
        //JFrame.remove(loginPanel);
        //JFrame.add(waitingRoomPanel);
        //Jframe.setVisible(false);
        //Jframe.setVisible(true)
    }

}
