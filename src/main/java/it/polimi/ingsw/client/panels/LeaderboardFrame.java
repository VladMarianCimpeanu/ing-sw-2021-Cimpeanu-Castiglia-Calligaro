package it.polimi.ingsw.client.panels;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

/**
 * frame containing that displays the game's result.
 */
public class LeaderboardFrame extends JFrame {
    public LeaderboardFrame(ArrayList<String> order, Map<String, Integer> ranks){
        this.setTitle("Maestri del Rinascimento");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(8, 1));
        this.setSize(new Dimension(500, 300));
        this.add(new JLabel("The game is ended. This is the Leaderboard"));
        if(order.contains("blackCross")){
            if(order.get(0).equals("blackCross"))
                this.add(new JLabel("You're a looser"));
            else
                this.add(new JLabel("You've won and your score is: "+ranks.get(order.get(0))));
        }else{
            for(String name: order)
                this.add(new JLabel((order.indexOf(name)+1)+")"+name+" "+ranks.get(name)));
        }
        this.setVisible(true);
    }
}
