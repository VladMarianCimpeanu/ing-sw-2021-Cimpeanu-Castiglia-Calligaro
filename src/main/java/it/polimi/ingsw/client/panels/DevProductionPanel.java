package it.polimi.ingsw.client.panels;

import it.polimi.ingsw.client.Clickable;
import it.polimi.ingsw.client.GUI;
import it.polimi.ingsw.client.MessageFromServer.ErrorMessage;
import it.polimi.ingsw.client.MessageToServer.ActivateCardProduction;
import it.polimi.ingsw.client.MessageToServer.ActivateProduction;
import it.polimi.ingsw.client.Resource;
import it.polimi.ingsw.client.Shape;
import it.polimi.ingsw.client.modelLight.GUI.DepotGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Action panel showed when the player is making a development card production
 */
public class DevProductionPanel extends ActionPanel{
    private JButton activate;
    private JLabel title;
    public DevProductionPanel(){
        super();
        setLayout(null);
        GUI.getGamePanel().unlockGameBoard(false);
        GUI.getGamePanel().addAction((Clickable) GUI.getClient().getGameView().getPlayer(GUI.getClient().getNickname()).getDepot());
        GUI.getGamePanel().addAction((Clickable) GUI.getClient().getGameView().getPlayer(GUI.getClient().getNickname()).getStrongbox());
        GUI.getGamePanel().addAction((Clickable) GUI.getClient().getGameView().getPlayer(GUI.getClient().getNickname()).getLeaderCards());
        activate = new JButton("Activate Production");
        activate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUI.getClient().send(new ActivateProduction());
            }
        });
        activate.setBounds(100, 100, 200, activate.getPreferredSize().height);
        add(activate);
        title = new JLabel("Take these resources from your stocks");
        title.setBounds(10, 20, 300, title.getPreferredSize().height);
        add(title);
    }

    @Override
    public void displayError(ErrorMessage error) {
    }



    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Map<Resource, Integer> notPayed = GUI.getClient().getGameView().getResBuffer();
        int x = 10;
        int y = 60;
        for(Resource resource: notPayed.keySet()){
            g.drawString(notPayed.get(resource)+"x", x, y);
            x+= 10;
            GamePanel.drawImage(g, resource.url(), new Shape(x, y, 20, 20));
            x+= 20;
        }
    }


}
