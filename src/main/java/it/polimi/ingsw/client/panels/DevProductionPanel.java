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

public class DevProductionPanel extends ActionPanel{
    private JButton activate;
    private JLabel title;
    private ArrayList<Clickable> clickables;
    public DevProductionPanel(){
        super();
        setLayout(null);
        clickables = new ArrayList<>();
        clickables.add((Clickable) GUI.getClient().getGameView().getPlayer(GUI.getClient().getNickname()).getDepot());
        clickables.add((Clickable) GUI.getClient().getGameView().getPlayer(GUI.getClient().getNickname()).getStrongbox());
        //add extraslot
        activate = new JButton("Activate Production");
        activate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUI.getClient().send(new ActivateProduction());

            }
        });
        activate.setBounds(100, 100, activate.getPreferredSize().width, activate.getPreferredSize().height);
        add(activate);
        title = new JLabel("Take these resources from your stocks");
        title.setBounds(10, 20, title.getPreferredSize().width, title.getPreferredSize().height);
        add(title);
        addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e){
                activeClick(e.getX(),e.getY());
            }
        });
    }

    @Override
    public void displayError(ErrorMessage error) {
    }

    private void activeClick(int x, int y) {
        for (Clickable c : new ArrayList<>(clickables)) {
            if (c.isClicked(x, y)) {
                c.click(x, y);
            }
        }
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Map<Resource, Integer> notPayed = GUI.getClient().getGameView().getResBuffer();
        int x = 10;
        int y = 50;
        for(Resource resource: notPayed.keySet()){
            g.drawString(notPayed.get(resource)+"x", x, y);
            x+= 10;
            GamePanel.drawImage(g, resource.url(), new Shape(x, y, 20, 20));
            x+= 20;
        }
    }


}
