package it.polimi.ingsw.client.panels;

import it.polimi.ingsw.client.Clickable;
import it.polimi.ingsw.client.GUI;
import it.polimi.ingsw.client.MessageFromServer.ErrorMessage;
import it.polimi.ingsw.client.MessageToServer.ActivateProduction;
import it.polimi.ingsw.client.MessageToServer.SelResIn;
import it.polimi.ingsw.client.MessageToServer.SelResOut;
import it.polimi.ingsw.client.Resource;
import it.polimi.ingsw.client.Shape;
import it.polimi.ingsw.client.modelLight.GUI.DepotGUI;
import it.polimi.ingsw.client.modelLight.GUI.GameGUI;
import it.polimi.ingsw.client.modelLight.PlayerView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Map;

/**
 * Action panel showed when the player is making an extra production
 */
public class ExtraProdPanel extends ActionPanel{
    private JLabel title1;
    private JButton button;
    private Resource resOut;
    private int phase;
    public ExtraProdPanel(){
        super();
        setLayout(null);
        GUI.getGamePanel().unlockGameBoard(false);
        PlayerView player = GUI.getClient().getGameView().getPlayer(GUI.getClient().getNickname());
        GUI.getGamePanel().addAction((Clickable) player.getDepot());
        GUI.getGamePanel().addAction((Clickable) player.getLeaderCards());
        GUI.getGamePanel().addAction((Clickable) player.getStrongbox());
        phase = 0;
        resOut = null;
        addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e){
                activeClick(e.getX(),e.getY());
            }
        });
        button = new JButton("Go to Payment");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(resOut == null) return;
                if(phase == 0){
                    GUI.getClient().send(new SelResOut(resOut));
                    ((GameGUI)GUI.getClient().getGameView()).setPayPanel("extra");
                }else
                    GUI.getClient().send(new ActivateProduction());
            }
        });
        button.setBounds(100, 200, 150, button.getPreferredSize().height);
        add(button);
        title1 = new JLabel("Choose the Resource to craft!");
        title1.setBounds(10, 20, 200, title1.getPreferredSize().height);
        add(title1);
    }

    /**
     * Allows to set the phase of the base production. This can be:
     * - 0: the user is choosing the output resource
     * - 1: the user is paying the input resources
     * @param phase
     */
    public void setPhase(int phase) {
        this.phase = phase;
    }

//    @Override
//    public void displayError(ErrorMessage error) {
//        if(error == ErrorMessage.productionUsed)
//            GUI.getClient().getGameView().lastProduced(null, GUI.getClient().getNickname());
//    }

    @Override
    public void displayError(ErrorMessage error) {
        if(error == ErrorMessage.notEnoughResources || error == ErrorMessage.productionUsed) {
            ((DepotGUI) GUI.getClient().getGameView().getPlayer(GUI.getClient().getNickname()).getDepot()).setStrategyMove();
            DefaultPanel defaultPanel = new DefaultPanel();
            GUI.getGamePanel().setActionPanel(defaultPanel);
            if(error == ErrorMessage.notEnoughResources)
                defaultPanel.setNotEnoughResource();
            else
                defaultPanel.setProductionUsed();
            GUI.getGamePanel().removeAllActions();
            GUI.getGamePanel().unlockGameBoard(true);
            GUI.getGamePanel().repaint();
        }
    }

    /**
     * Action triggered after the mouse click on the Action panel
     * @param x x axis value of the click
     * @param y y axis value of the click
     */
    public void activeClick(int x, int y){
        int xt = 10;
        int yt = 40;
        if(phase == 0){
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

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = 10;
        int y = 40;
        switch(phase){
            case 0: //selection input and output
                for(Resource resource: Resource.values()) {
                    if(resource.equals(Resource.FAITH)) continue;
                    GamePanel.drawImage(g, resource.url(), new it.polimi.ingsw.client.Shape(x, y, 20, 20));
                    x += 30;
                }

                x = 10;
                y = 70;
                if(resOut != null)
                    GamePanel.drawImage(g, resOut.url(), new Shape(x, y, 20, 20));
                break;
            case 1: //take from stock to pay
                Map<Resource, Integer> notPayed = GUI.getClient().getGameView().getResBuffer();
                button.setText("Activate Production");
                title1.setText("Take these resources from your stocks");
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
