package it.polimi.ingsw.client.panels;

import it.polimi.ingsw.client.Clickable;
import it.polimi.ingsw.client.GUI;
import it.polimi.ingsw.client.MessageFromServer.ErrorMessage;
import it.polimi.ingsw.client.modelLight.PlayerView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class MoveExtraPanel extends ActionPanel implements ActionListener{
    private int howMany;
    private JComboBox<String> comboBox;

    public MoveExtraPanel(int max){
        GUI.getGamePanel().unlockGameBoard(false);
        PlayerView player = GUI.getClient().getGameView().getPlayer(GUI.getClient().getNickname());
        GUI.getGamePanel().addAction((Clickable) player.getDepot());
        JLabel label1 = new JLabel("Choose how many resources you want to move");
        add(label1);
        String[] possibleChoices = new String[max];
        for(int i = 1; i<=max; i++)
            possibleChoices[i-1] = String.valueOf(i);
        comboBox = new JComboBox<String>(possibleChoices);
        add(comboBox);
        comboBox.addActionListener(this);
        if(max == 0) howMany = 0;
        else howMany = 1;
    }

    public int getHowMany() {
        return howMany;
    }

    @Override
    public void displayError(ErrorMessage error) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == comboBox)
            howMany = Integer.parseInt((String)comboBox.getSelectedItem());
    }
}
