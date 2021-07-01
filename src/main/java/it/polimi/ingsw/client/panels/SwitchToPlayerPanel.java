package it.polimi.ingsw.client.panels;

import it.polimi.ingsw.client.Clickable;
import it.polimi.ingsw.client.GUI;
import it.polimi.ingsw.client.MessageToServer.CheatResource;
import it.polimi.ingsw.client.MessageToServer.EndTurn;
import it.polimi.ingsw.client.modelLight.GUI.FaithPathGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * this class displays the players that are playing the current game and let the player to select
 * which dashboard must be shown.
 */
public class SwitchToPlayerPanel extends JPanel implements ActionListener {
    private ArrayList<PlayerButton> playerButtons;
    private ArrayList<Clickable> lastActionClickable;
    private boolean gameBoardUnlocked;
    private JButton passButton;
    private JButton cheatButton; //TODO delete this, only for beta testing
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
        passButton = new JButton("Pass");
        passButton.addActionListener(this);
        cheatButton = new JButton("cheat");
        cheatButton.addActionListener(this);
        add(passButton);
        add(cheatButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(PlayerButton button: playerButtons)
            if (e.getSource() == button){
                GUI.getGamePanel().setPlayerWatched(button.getPlayer());
                GUI.getGamePanel().repaint();
            }
        if(e.getSource() == passButton){
            GUI.sendMessage(new EndTurn());
        }
        if(e.getSource() == cheatButton) GUI.sendMessage(new CheatResource());
    }
}

/**
 * JButton class associated with a string representing a Nickname.
 */
class PlayerButton extends JButton{
    private String player;
    PlayerButton(String nickname){
        FaithPathGUI faithPathGUI = (FaithPathGUI) GUI.getClient().getGameView().getFaithPath();
        String color = faithPathGUI.getCrosses().get(nickname);
        Color buttonColor = Color.WHITE;
        switch(color){
            case "images/punchboard/faith.png":
                buttonColor = Color.RED;
                break;
            case "images/punchboard/faithGreen.png":
                buttonColor = new Color(11, 85, 11);
                break;
            case "images/punchboard/faithBlue.png":
                buttonColor = Color.BLUE;
                break;
            case "images/punchboard/faithYellow.png":
                buttonColor = Color.YELLOW;
                break;
        }
        setForeground(buttonColor);
        setText(nickname + "'s board");
        player = nickname;
    }

    /**
     * gives the nickname associated with this button.
     * @return the nickname associated with this button.
     */
    String getPlayer(){
        return player;
    }
}

