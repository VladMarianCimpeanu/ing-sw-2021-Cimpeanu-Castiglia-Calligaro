package it.polimi.ingsw.client.panels;

import it.polimi.ingsw.client.Clickable;
import it.polimi.ingsw.client.GUI;
import it.polimi.ingsw.client.MessageFromServer.ErrorMessage;
import it.polimi.ingsw.client.modelLight.GUI.LeaderCardSetGUI;
import it.polimi.ingsw.client.modelLight.PlayerView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Action panel showed when the player is moving some resources from a shelf of the warehouse depot to somewhere
 */
public class MoveResPanel extends ActionPanel implements MovePanel{
    private ActionPanel lastPanel;
    private boolean boardUnlocked;
    private ArrayList<Clickable> lastClickable;

    public MoveResPanel(ActionPanel lastPanel){
        boardUnlocked = GUI.getGamePanel().isGameBoardClickable();
        lastClickable = GUI.getGamePanel().getAction();
        GUI.getGamePanel().unlockGameBoard(false);
        this.lastPanel = lastPanel;
        PlayerView player = GUI.getClient().getGameView().getPlayer(GUI.getClient().getNickname());
        GUI.getGamePanel().removeAllActions();
        GUI.getGamePanel().addAction((Clickable) player.getDepot());
        ((LeaderCardSetGUI) GUI.getClient().getGameView().getPlayer(GUI.getClient().getNickname()).getLeaderCards()).setSlotsClickable();
        JLabel label1 = new JLabel("Choose where to move the resources just selected");
        add(label1);
    }

    @Override
    public void displayError(ErrorMessage error) {
    }

    public void paintComponent(Graphics g){
    }

    @Override
    public boolean wasBoardUnlocked() {
        return boardUnlocked;
    }

    @Override
    public ActionPanel getLastPanel(){
        return lastPanel;
    }

    @Override
    public void restoreClickable() {
        GUI.getGamePanel().removeAllActions();
        for(Clickable clickable: lastClickable) GUI.getGamePanel().addAction(clickable);
    }

    @Override
    public ArrayList<Clickable> getLastClickable() {
        return lastClickable;
    }
}
