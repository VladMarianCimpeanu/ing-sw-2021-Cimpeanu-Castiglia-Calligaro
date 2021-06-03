package it.polimi.ingsw.client.panels;

import it.polimi.ingsw.client.Resource;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class WaitingRoomPanel extends JPanel {
    private ArrayList<String> nicknames;
    private JLabel roomSize;
    private JLabel players;

    public WaitingRoomPanel(int size, ArrayList<String> waitingPlayers) {
        nicknames = new ArrayList<>();
        this.setBackground(Color.white);

        roomSize = new JLabel(("Room size: " + size));
        players = new JLabel(("Players: "));

        for(String player: waitingPlayers){
            addNickname(player);
        }

        this.add(roomSize);
        this.add(players);

        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
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
