package it.polimi.ingsw.client.modelLight.GUI;

import it.polimi.ingsw.client.Clickable;
import it.polimi.ingsw.client.Color;
import it.polimi.ingsw.client.GUI;
import it.polimi.ingsw.client.MessageToServer.ActivateCardProduction;
import it.polimi.ingsw.client.MessageToServer.BuyDevCard;
import it.polimi.ingsw.client.MessageToServer.PlaceCard;
import it.polimi.ingsw.client.Shape;
import it.polimi.ingsw.client.modelLight.DevelopmentCardView;
import it.polimi.ingsw.client.panels.BuyPanel;
import it.polimi.ingsw.client.panels.DevProductionPanel;

public class DevelopmentCardGUI extends DevelopmentCardView implements Clickable {
    private Shape shape;
    private String url;
    private int ID;
    private int Level;
    private Color Color;
    private final static int width = 79;
    private final static int height =120;
    private Runnable actionOnClick;

    public void setPosition(int x, int y) {
        this.shape = new Shape(x, y, width, height);
    }

    public Shape getShape(){
        return shape;
    }

    public String getUrl(){
        return "images/dev/" + url;
    }

    public void setToBuyable(){

        actionOnClick = () -> {
            ((GameGUI)GUI.getClient().getGameView()).setPayPanel("buy");
            GUI.sendMessage(new BuyDevCard(Level, Color));
        };
    }

    public void setToProduction(){
        actionOnClick = () -> {
            int pos = GUI.getClient().getGameView().getPlayer(GUI.getClient().getNickname()).getDecks().getPos(ID);
            ((GameGUI)GUI.getClient().getGameView()).setPayPanel("production");
            GUI.getClient().send(new ActivateCardProduction(pos));
        };
    }

    //TODO
    public void setToReplaceable(){
        actionOnClick = () -> {
            int pos = GUI.getClient().getGameView().getPlayer(GUI.getClient().getNickname()).getDecks().getPos(ID);
            GUI.getClient().send(new PlaceCard(pos));
        };
    }

    public static int getWidth(){
        return width;
    }

    public static int getHeight(){
        return height;
    }

    @Override
    public void show() {
    }

    @Override
    public boolean isClicked(int x, int y) {
        return (shape.getX() <= x && x <= shape.getX() + width) && (shape.getY() <= y && y <= shape.getY() + height);
    }

    @Override
    public void click(int x, int y) {
        actionOnClick.run();
    }

}
