package it.polimi.ingsw.client.panels;

import it.polimi.ingsw.client.Clickable;
import it.polimi.ingsw.client.GUI;
import it.polimi.ingsw.client.MessageFromServer.ErrorMessage;
import it.polimi.ingsw.client.Resource;
import it.polimi.ingsw.client.Shape;
import it.polimi.ingsw.client.modelLight.GUI.DepotGUI;
import it.polimi.ingsw.client.modelLight.GUI.DevelopmentCardDecksGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BuyPanel extends ActionPanel{
    private JLabel title;
    private ArrayList<Clickable> clickables;
    public BuyPanel(){
        super();
        setLayout(null);
        clickables = new ArrayList<>();
        clickables.add((Clickable) GUI.getClient().getGameView().getPlayer(GUI.getClient().getNickname()).getDepot());
        clickables.add((Clickable) GUI.getClient().getGameView().getPlayer(GUI.getClient().getNickname()).getStrongbox());
        clickables.add((Clickable) GUI.getClient().getGameView().getPlayer(GUI.getClient().getNickname()).getDecks());
        //add extraslot
        title = new JLabel("Take these resources from your stocks");
        title.setBounds(10, 20, title.getPreferredSize().width, title.getPreferredSize().height);
        add(title);
        addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e){
                activeClick(e.getX(),e.getY());
            }
        });
        //((DevelopmentCardDecksGUI)GUI.getClient().getGameView().getPlayer(GUI.getClient().getNickname()).getDecks()).setToReplaceable();
    }

    @Override
    public void displayError(ErrorMessage error) {
        if(error == ErrorMessage.requirementsNotSatisfied){
            ((DepotGUI)GUI.getClient().getGameView().getPlayer(GUI.getClient().getNickname()).getDepot()).setStrategyMove();
            GUI.getGamePanel().setActionPanel(new DefaultPanel());
            clickables.clear();
        }
    }

    private void activeClick(int x, int y) {
        for (Clickable c : new ArrayList<>(clickables)) {
            if (c.isClicked(x, y)) {
                c.click(x, y);
            }
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Map<Resource, Integer> notPayed = GUI.getClient().getGameView().getResBuffer();
        if(notPayed.isEmpty())
            title.setText("Place the card on one deck!");
        else{
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
}
