package it.polimi.ingsw.client.panels;

import it.polimi.ingsw.client.Clickable;
import it.polimi.ingsw.client.GUI;
import it.polimi.ingsw.client.MessageFromServer.ErrorMessage;
import it.polimi.ingsw.client.MessageToServer.ActivateProduction;
import it.polimi.ingsw.client.MessageToServer.SelResIn;
import it.polimi.ingsw.client.MessageToServer.SelResOut;
import it.polimi.ingsw.client.Resource;
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
import it.polimi.ingsw.client.Shape;
import it.polimi.ingsw.client.modelLight.GUI.GameGUI;
import it.polimi.ingsw.server.model.benefit.Faith;

/**
 * Action panel showed when the player is making a base production
 */
public class BaseProdPanel extends ActionPanel{
    private JButton button;
    private JLabel title1;
    private JLabel title2;
    private Resource resOut;
    private int phase;

    public BaseProdPanel(){
        super();
        setLayout(null);
        GUI.getGamePanel().unlockGameBoard(false);
        GUI.getGamePanel().addAction((Clickable) GUI.getClient().getGameView().getPlayer(GUI.getClient().getNickname()).getDepot());
        GUI.getGamePanel().addAction((Clickable) GUI.getClient().getGameView().getPlayer(GUI.getClient().getNickname()).getStrongbox());
        GUI.getGamePanel().addAction((Clickable) GUI.getClient().getGameView().getPlayer(GUI.getClient().getNickname()).getLeaderCards());
        GUI.getClient().getGameView().getResBuffer().clear();
        button = new JButton("Go to payment");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Resource> resIn = new ArrayList<>();
                for(Resource resource: GUI.getClient().getGameView().getResBuffer().keySet())
                    for(int i = 0; i<GUI.getClient().getGameView().getResBuffer().get(resource); i++)
                        resIn.add(resource);
                if(resIn.size() != 2 && resOut == null) return;
                if(phase == 0){
                    GUI.getClient().send(new SelResIn(resIn.get(0), resIn.get(1)));
                    GUI.getClient().send(new SelResOut(resOut));
                    ((GameGUI)GUI.getClient().getGameView()).setPayPanel("base");
                }else {
                    GUI.getClient().send(new ActivateProduction());
                    ((DepotGUI) GUI.getClient().getGameView().getPlayer(GUI.getClient().getNickname()).getDepot()).setStrategyMove();
                    GUI.getGamePanel().setActionPanel(new DefaultPanel());
                }
                repaint();
            }
        });
        button.setBounds(100, 200, button.getPreferredSize().width, button.getPreferredSize().height);
        add(button);
        title1 = new JLabel("Choose 2 input resources!");
        title1.setBounds(10, 20, title1.getPreferredSize().width, title1.getPreferredSize().height);
        add(title1);
        title2 = new JLabel("Choose the output resource!");
        title2.setBounds(10, 110, title2.getPreferredSize().width, title2.getPreferredSize().height);
        add(title2);
        addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e){
                activeClick(e.getX(),e.getY());
            }
        });
        resOut = null;
        phase = 0;
    }

    /**
     * Allows to set the phase of the base production. This can be:
     * - 0: the user is choosing the input and output resources
     * - 1: the user is paying the input resources
     * @param phase
     */
    public void setPhase(int phase) {
        this.phase = phase;
    }

    @Override
    public void displayError(ErrorMessage error) {
        if(error == ErrorMessage.notEnoughResources) {
            ((DepotGUI) GUI.getClient().getGameView().getPlayer(GUI.getClient().getNickname()).getDepot()).setStrategyMove();
            DefaultPanel defaultPanel = new DefaultPanel();
            GUI.getGamePanel().setActionPanel(defaultPanel);
            defaultPanel.setNotEnoughResource();
            GUI.getGamePanel().removeAllActions();
            GUI.getGamePanel().unlockGameBoard(true);
            GUI.getGamePanel().repaint();
        }else if(error == ErrorMessage.productionUsed)
            ((DefaultPanel)GUI.getGamePanel().getActionPanel()).setProductionUsed();
    }

    /**
     * Action triggered after the mouse click on the Action panel
     * @param x x axis value of the click
     * @param y y axis value of the click
     */
    private void activeClick(int x, int y) {
        int xt = 10;
        int yt = 40;
        if(phase == 0){
            Map<Resource, Integer> resources = GUI.getClient().getGameView().getResBuffer();

            int quantity = 0;
            for(Resource resource: resources.keySet())
                quantity += resources.get(resource);

            for(Resource resource: Resource.values()){
                if(resource.equals(Resource.FAITH)) continue;
                if(xt <= x && x <= xt+20 && yt <= y && y <= yt+20){
                    if(quantity < 2)
                        if(resources.containsKey(resource))
                            resources.put(resource, resources.get(resource)+1);
                        else resources.put(resource, 1);
                    break;
                }
                xt += 30;
            }

            xt = 10;
            yt = 130;
            for(Resource resource: Resource.values()){
                if(resource.equals(Resource.FAITH)) continue;
                if(xt <= x && x <= xt+20 && yt <= y && y <= yt+20){
                    if(resOut == null) resOut = resource;
                    break;
                }
                xt += 30;
            }
            repaint();
        }
    }


    public void paintComponent(Graphics g){
        super.paintComponent(g);
        int x = 10;
        int y = 40;
        switch(phase){
            case 0: //selection input and output
                Map<Resource, Integer> resIn = GUI.getClient().getGameView().getResBuffer();
                for(Resource resource: Resource.values()) {
                    if(resource.equals(Resource.FAITH)) continue;
                    GamePanel.drawImage(g, resource.url(), new Shape(x, y, 20, 20));
                    x += 30;
                }
                x = 10;
                y = 70;
                for(Resource resource: resIn.keySet())
                    for(int i = 0; i<resIn.get(resource); i++) {
                        GamePanel.drawImage(g, resource.url(), new Shape(x, y, 20, 20));
                        x += 30;
                    }

                x = 10;
                y = 130;
                for(Resource resource: Resource.values()) {
                    if(resource.equals(Resource.FAITH)) continue;
                    GamePanel.drawImage(g, resource.url(), new Shape(x, y, 20, 20));
                    x += 30;
                }
                x = 10;
                y = 160;
                if(resOut != null)
                    GamePanel.drawImage(g, resOut.url(), new Shape(x, y, 20, 20));

                break;
            case 1: //take from stock to pay
                Map<Resource, Integer> notPayed = GUI.getClient().getGameView().getResBuffer();
                button.setText("Activate Production");
                title1.setText("Take these resources from your stocks");
                title2.setVisible(false);
                for(Resource resource: notPayed.keySet()){
                    g.drawString(notPayed.get(resource)+"x", x, y);
                    x+= 10;
                    GamePanel.drawImage(g, resource.url(), new it.polimi.ingsw.client.Shape(x, y, 20, 20));
                    x+= 20;
                }
                break;


        }
    }




}
