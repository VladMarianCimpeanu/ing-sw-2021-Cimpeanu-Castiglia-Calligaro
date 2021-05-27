package it.polimi.ingsw.client.panels;

import it.polimi.ingsw.client.GUI;
import it.polimi.ingsw.client.MessageToServer.EndTurn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SwitchToPlayerPanel extends JPanel implements ActionListener {
    private ArrayList<PlayerButton> playerButtons;
    private JButton passButton;
    public SwitchToPlayerPanel(){
        setBackground(Color.WHITE);
        setLayout(new GridLayout(3, GUI.getClient().getTurns().size()));
        this.add(new JLabel("Click a button bellow to change board"));
        for(int i = 0; i < (GUI.getClient().getTurns().size() - 1); i ++) this.add(new JLabel());
        setBounds(0, 700, 600, 50);
        playerButtons = new ArrayList<>();
        for(String nickname: GUI.getClient().getTurns()) {
            PlayerButton button = new PlayerButton(nickname);
            button.addActionListener(this);
            playerButtons.add(button);
            this.add(button);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(PlayerButton button: playerButtons)
            if (e.getSource() == button){
                GUI.getGamePanel().setPlayerWatched(button.getPlayer());
                GUI.getGamePanel().repaint();
            }
    }
}


class PlayerButton extends JButton{
    private String player;
    PlayerButton(String nickname){
        setText(nickname + "'s board");
        player = nickname;
    }

    String getPlayer(){
        return player;
    }
}

