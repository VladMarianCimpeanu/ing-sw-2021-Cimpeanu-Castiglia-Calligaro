package it.polimi.ingsw.client;

import it.polimi.ingsw.client.MessageToServer.ActivateBaseProduction;
import it.polimi.ingsw.client.MessageToServer.KeepLeaderCard;
import it.polimi.ingsw.client.MessageToServer.MessageToServer;
import it.polimi.ingsw.client.panels.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Map;

/**
 * GUI class, created when a client starts the Client with "gui" argument
 */
public class GUI {
    private static JFrame jFrame;
    private static Client client;
    private static LoginPanel loginPanel;
    private static WaitingRoomPanel waitingRoomPanel;
    private static int leaderToKeep = 0;
    private static GamePanel gamePanel = new GamePanel();

    public static void setClient(Client client) {
        GUI.client = client;
    }

    /**
     * send a message to the server
     */
    public static void sendMessage(MessageToServer message){
        client.send(message);
    }

    /**
     * creates on a separate thread the GUI using Swing
     */
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

    /**
     * Change the login window, with the waiting room window
     * @param size Size of the waiting room
     * @param players List of players currently waiting in the lobby
     */
    public static void goToWaitingRoom(int size, ArrayList<String> players){
        jFrame.remove(loginPanel);
        waitingRoomPanel = new WaitingRoomPanel(size, players);
        jFrame.add(waitingRoomPanel);
        jFrame.setVisible(false);
        jFrame.setVisible(true);
    }

    /**
     * Change the waiting room window, with the game window
     */
    public static void goToGame(){
        if(waitingRoomPanel != null) {
            jFrame.remove(waitingRoomPanel);
        }else{
            jFrame.remove(loginPanel);
        }
        jFrame.add(gamePanel);
        jFrame.setSize(1280,720);
        jFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        gamePanel.setPlayerWatched(GUI.getClient().getNickname());
        gamePanel.addOtherPlayersPanel();
        addBaseProd();

        jFrame.setVisible(false);
        jFrame.setVisible(true);
    }

    /**
     * Store the selected leaderCards to keep:
     * when they are 2, sends the KeepLeaderCard Message to the server.
     * @param id of the selected leaderCard
     */
    public static void keepLeader(int id){
        if(leaderToKeep == 0){
            leaderToKeep = id;
        }else{
            sendMessage(new KeepLeaderCard(leaderToKeep, id, client.getNickname()));
            leaderToKeep = 0;
        }
    }

    /**
     * @param string String to print
     */
    public static void print(String string){
        gamePanel.getScrollPanel().getMessagesPanel().addMessage(string);
    }

    private static void addBaseProd(){
        gamePanel.addGameboard(new Clickable() {
            @Override
            public boolean isClicked(int x, int y) {
                if(150 <= x && x <= 220 && 285 <= y && y <= 355){
                    return true;
                }
                return false;
            }

            @Override
            public void click(int x, int y) {
                GUI.getClient().send(new ActivateBaseProduction());
            }
        });
    }

    /**
     * closes the current frame and displays an error with the connection.
     */
    public static void closeGame(){
        jFrame.dispose();
        jFrame = new CrashFrame();
        jFrame.setVisible(true);
    }

    /**
     * closes the current frame and it opens a new frame containing scores of the players.
     * @param order an array of nicknames ordered by the amount of points scored
     * @param ranks a map containing the amount of points earned by each player.
     */
    public static void showPoints(ArrayList<String> order, Map<String, Integer> ranks){
        jFrame.dispose();
        jFrame = new LeaderboardFrame(order, ranks);
        jFrame.setVisible(true);
    }
}
