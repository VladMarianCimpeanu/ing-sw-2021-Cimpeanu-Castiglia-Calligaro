package it.polimi.ingsw.client.panels;

import it.polimi.ingsw.client.Clickable;
import it.polimi.ingsw.client.GUI;
import it.polimi.ingsw.client.MessageFromServer.ErrorMessage;
import it.polimi.ingsw.client.modelLight.PlayerView;

import javax.swing.*;
import java.awt.*;

/**
 * Action panel showed when the player is moving some resources from a shelf of the warehouse depot to somewhere
 */
public class MoveResPanel extends ActionPanel{
    public MoveResPanel(){
        GUI.getGamePanel().unlockGameBoard(false);
        PlayerView player = GUI.getClient().getGameView().getPlayer(GUI.getClient().getNickname());
        GUI.getGamePanel().addAction((Clickable) player.getDepot());
        GUI.getGamePanel().addAction((Clickable) player.getLeaderCards());
        JLabel label1 = new JLabel("Choose where to move the resources just selected");
        add(label1);
    }

    @Override
    public void displayError(ErrorMessage error) {
    }

    public void paintComponent(Graphics g){
    }
}
