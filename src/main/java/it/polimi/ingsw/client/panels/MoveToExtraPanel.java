package it.polimi.ingsw.client.panels;

import it.polimi.ingsw.client.Clickable;
import it.polimi.ingsw.client.GUI;
import it.polimi.ingsw.client.MessageFromServer.ErrorMessage;
import it.polimi.ingsw.client.MessageToServer.MoveWarehouseToExtra;
import it.polimi.ingsw.client.modelLight.GUI.DepotGUI;
import it.polimi.ingsw.client.modelLight.GUI.LeaderCardGUI;
import it.polimi.ingsw.client.modelLight.PlayerView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Action panel showed when the player is moving some resources from a shelf to a extra slot and has to choose the number of resources to move
 */
public class MoveToExtraPanel extends ActionPanel implements ActionListener, MovePanel {
    private JComboBox<String> comboBox;
    private LeaderCardGUI card;
    private ActionPanel lastPanel;
    private boolean boardUnlocked;
    private ArrayList<Clickable> lastClickable;

    public MoveToExtraPanel(LeaderCardGUI card, ActionPanel lastPanel){
        GUI.getGamePanel().unlockGameBoard(false);
        PlayerView player = GUI.getClient().getGameView().getPlayer(GUI.getClient().getNickname());
        GUI.getGamePanel().addAction((Clickable) player.getDepot());
        lastClickable = GUI.getGamePanel().getAction();
        boardUnlocked = GUI.getGamePanel().isGameBoardClickable();
        JLabel label1 = new JLabel("Choose how many resources you want to move");
        add(label1);
        String[] possibleChoices;
        DepotGUI depotGUI = (DepotGUI) player.getDepot();
        if(depotGUI.getQuantity(depotGUI.getShelfFrom()) < 2){
            possibleChoices = new String[depotGUI.getQuantity(depotGUI.getShelfFrom())];
            for(int i = 1; i<=depotGUI.getQuantity(depotGUI.getShelfFrom()); i++)
                possibleChoices[i-1] = String.valueOf(i);
        }else{
            possibleChoices = new String[2];
            for(int i = 1; i<=2; i++)
                possibleChoices[i-1] = String.valueOf(i);
        }
        comboBox = new JComboBox<String>(possibleChoices);
        add(comboBox);
        comboBox.addActionListener(this);
        this.card = card;
        this.lastPanel = lastPanel;
    }
    @Override
    public void displayError(ErrorMessage error) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == comboBox) {
            PlayerView player = GUI.getClient().getGameView().getPlayer(GUI.getClient().getNickname());
            GUI.getClient().send(new MoveWarehouseToExtra(((DepotGUI)player.getDepot()).getShelfFrom(), card.getID(), Integer.parseInt((String) Objects.requireNonNull(comboBox.getSelectedItem()))));
            ((DepotGUI)player.getDepot()).resetShelfFrom();
            card.revalidateActionPanel();
        }
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
}
