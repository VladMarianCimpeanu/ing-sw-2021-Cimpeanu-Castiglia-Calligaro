package it.polimi.ingsw.client.panels;

import it.polimi.ingsw.client.Clickable;
import it.polimi.ingsw.client.Color;
import it.polimi.ingsw.client.GUI;
import it.polimi.ingsw.client.MessageFromServer.ErrorMessage;
import it.polimi.ingsw.client.MessageToServer.BuyDevCard;
import it.polimi.ingsw.client.modelLight.GUI.LeaderCardGUI;
import it.polimi.ingsw.client.modelLight.GUI.LeaderCardSetGUI;
import it.polimi.ingsw.client.Shape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class DiscountPanel extends ActionPanel implements ActionListener {
    private int level;
    private Color color;
    private ArrayList<Integer> discounts;

    public DiscountPanel(){
        super();
        setLayout(null);
        GUI.getGamePanel().unlockGameBoard(false);
        GUI.getGamePanel().addAction((Clickable)GUI.getClient().getGameView().getPlayer(GUI.getClient().getNickname()).getLeaderCards());
        JLabel title = new JLabel("Choose the discounts you want to use for this purchase");
        title.setBounds(10, 20, title.getPreferredSize().width, title.getPreferredSize().height);
        add(title);
        JButton button = new JButton("Go to payment");
        button.setBounds(40, 150, button.getPreferredSize().width, button.getPreferredSize().height);
        add(button);
        button.addActionListener(this);
        level = 0;
        color = null;
        discounts = new ArrayList<>();
    }


    public void addDiscount(int id){
        discounts.add(id);
        repaint();
    }


    public void setColor(Color color) {
        this.color = color;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public void displayError(ErrorMessage error) {
        if(error == ErrorMessage.notEnoughResources || error == ErrorMessage.invalidCommand || error == ErrorMessage.wrongLevel){
            DefaultPanel panel = new DefaultPanel();
            GUI.getGamePanel().setActionPanel(panel);
            if(error == ErrorMessage.notEnoughResources) panel.setNotEnoughResource();
            else if(error == ErrorMessage.wrongLevel) panel.setWrongLevel();
            GUI.getGamePanel().repaint();
            GUI.getGamePanel().removeAction((Clickable) GUI.getClient().getGameView().getPlayer(GUI.getClient().getNickname()).getDepot());
            GUI.getGamePanel().removeAction((Clickable) GUI.getClient().getGameView().getPlayer(GUI.getClient().getNickname()).getStrongbox());
            GUI.getGamePanel().removeAction((Clickable) GUI.getClient().getGameView().getPlayer(GUI.getClient().getNickname()).getLeaderCards());
            GUI.getGamePanel().removeAction((Clickable) GUI.getClient().getGameView().getPlayer(GUI.getClient().getNickname()).getDecks());
            GUI.getGamePanel().unlockGameBoard(true);
            ((LeaderCardSetGUI)GUI.getClient().getGameView().getPlayer(GUI.getClient().getNickname()).getLeaderCards()).setLeadersToDefaultStrategy();
        }
    }


    public void paintComponent(Graphics g){
        super.paintComponent(g);

        int x = 25;
        int y = 40;

        for(int id: discounts){
            LeaderCardGUI card = LeaderCardSetGUI.getLeaderCard(id);
            Shape shape = card.getShape();
            GamePanel.drawImage(g, card.getImage(), new Shape(x, y, shape.getWidth()/2, shape.getHeight()/2));
            x += 80;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(discounts.isEmpty())
            GUI.getClient().send(new BuyDevCard(level, color));
        else
            GUI.getClient().send(new BuyDevCard(level, color, discounts));
    }
}
