package it.polimi.ingsw.client.panels;

import javax.swing.*;
import java.awt.Color;
import java.util.ArrayList;

public class WaitingRoomPanel extends JPanel {
    private ArrayList<String> nicknames;
    private JLabel roomSize;
    private JLabel players;

    public WaitingRoomPanel() {
        nicknames = new ArrayList<>();
        this.setBackground(Color.white);

        roomSize = new JLabel("Room size: 3");
        players = new JLabel("Players: ");

        this.add(roomSize);
        this.add(players);

        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    }

    //TODO: delete this main, it's just for testing.
    public static void main(String[] args) {
        JFrame f = new JFrame("Maestri del rinascimento");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        WaitingRoomPanel waitingRoomPanel = new WaitingRoomPanel();
        f.add(waitingRoomPanel);
        f.setSize(400, 190);
        f.setVisible(true);
    }

    public void addNickname(String nickname){
        nicknames.add(nickname);
        String list = "Players: ";
        for(String n: nicknames){
            list += (n + " ");
        }
        players.setText(list);
    }

    public void removeNickname(String nickname){
        nicknames.remove(nickname);
        String list = "Players: ";
        for(String n: nicknames){
            list += (n + " ");
        }
        players.setText(list);
    }
}
