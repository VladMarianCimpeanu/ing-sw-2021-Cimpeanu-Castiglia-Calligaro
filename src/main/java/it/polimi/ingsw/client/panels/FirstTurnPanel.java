package it.polimi.ingsw.client.panels;

import it.polimi.ingsw.client.GUI;
import it.polimi.ingsw.client.MessageFromServer.ErrorMessage;
import it.polimi.ingsw.client.MessageToServer.ChooseFirstResources;
import it.polimi.ingsw.client.Resource;
import it.polimi.ingsw.client.modelLight.GUI.DepotGUI;
import it.polimi.ingsw.client.modelLight.GUI.LeaderCardSetGUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class FirstTurnPanel extends ActionPanel{
    private int numberRes;
    private ArrayList<Resource> res;
    private ArrayList<Integer> pos;

    public FirstTurnPanel() {
        super();
        add(new JLabel("Select 2 Leader Cards."));
        res = new ArrayList<>();
        pos = new ArrayList<>();
        numberRes = 0;
    }

    @Override
    public void displayError(ErrorMessage error) {
        //TODO
    }

    public void selectRes(int position, int numberRes) {
        LeaderCardSetGUI leaderCards = (LeaderCardSetGUI) GUI.getClient().getGameView().getPlayer(GUI.getClient().getNickname()).getLeaderCards();
        GUI.getGamePanel().removeAction(leaderCards);
        this.numberRes = numberRes;
        removeAll();
        if(numberRes == 0){
            GUI.getGamePanel().setActionPanel(new DefaultPanel());
            DepotGUI depotGUI = (DepotGUI) GUI.getClient().getGameView().getPlayer(GUI.getClient().getNickname()).getDepot();
            depotGUI.setStrategyMove();
        }
        if(numberRes > 0){
            add(new JLabel("Select " + numberRes + " resources"));

            //prints the Resources
            for(Resource r: Resource.values()){
                if(!r.equals(Resource.FAITH)){
                    InputStream url = GamePanel.class.getResourceAsStream("/" + r.url());
                    BufferedImage img= null;
                    try {
                        img = ImageIO.read(url);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return;
                    }
                    Image dimg = img.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
                    ImageIcon x = new ImageIcon(dimg);
                    JLabel label = new JLabel(x);
                    label.addMouseListener(new MouseAdapter() {
                        public void mouseClicked(MouseEvent e) {
                            if(res.size() == pos.size() && res.size() < numberRes) {
                                res.add(r);
                                DepotGUI depotGUI = (DepotGUI) GUI.getClient().getGameView().getPlayer(GUI.getClient().getNickname()).getDepot();
                                GUI.getGamePanel().addAction(depotGUI);
                            }
                        }
                    });
                    add(label);
                }
            }
            revalidate();
        }
    }

    public void selectPos(int shelf){
        if(pos.size() < res.size() && pos.size() < numberRes) {
            pos.add(shelf);
            DepotGUI depotGUI = (DepotGUI) GUI.getClient().getGameView().getPlayer(GUI.getClient().getNickname()).getDepot();
            GUI.getGamePanel().removeAction(depotGUI);
        }
        if(pos.size() == res.size() && res.size() == numberRes){
            if(numberRes == 2)
                GUI.sendMessage(new ChooseFirstResources(res.get(0), res.get(1), pos.get(0), pos.get(1), GUI.getClient().getNickname()));
            else if(numberRes == 1)
                GUI.sendMessage(new ChooseFirstResources(res.get(0), pos.get(0), GUI.getClient().getNickname()));
            GUI.getGamePanel().setActionPanel(new DefaultPanel());
            DepotGUI depotGUI = (DepotGUI) GUI.getClient().getGameView().getPlayer(GUI.getClient().getNickname()).getDepot();
            depotGUI.setStrategyMove();
            GUI.getGamePanel().removeAction(depotGUI);
        }
    }

    @Override
    public boolean isFirstTurn(){
        return true;
    }
}
