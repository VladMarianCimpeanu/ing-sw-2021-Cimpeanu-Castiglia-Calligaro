package it.polimi.ingsw.client.modelLight.GUI;

import it.polimi.ingsw.client.Clickable;
import it.polimi.ingsw.client.GUI;
import it.polimi.ingsw.client.MessageToServer.Strategy;
import it.polimi.ingsw.client.Shape;
import it.polimi.ingsw.client.modelLight.LeaderCardView;
import it.polimi.ingsw.client.panels.LeaderCardsPanel;

public class LeaderCardGUI extends LeaderCardView implements Clickable {

    private int ID;
    private String image;
    private String type;
    private Shape shape;
    private Runnable strategy = () -> GUI.keepLeader(getID());
    private boolean activated = false;
    private Runnable defaultStrategy = () -> GUI.getGamePanel().setActionPanel(new LeaderCardsPanel(ID));


    public void setShape(Shape shape) {
        this.shape = shape;
    }

    @Override
    public void show() {

    }

    @Override
    public boolean isClicked(int x, int y) {
        return((shape.getX() <= x && x <= (shape.getX() + shape.getWidth())) && (shape.getY() <= y && y <= (shape.getY() + shape.getHeight())));
    }

    @Override
    public void click(int x, int y) {
        strategy.run();
        System.out.println("ciao " + strategy);
    }

    public Shape getShape() {
        return shape;
    }

    public String getImage() {
        return image;
    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public void activate(){
        activated = true;
        if(type.equals("production")) defaultStrategy = () ->{//TODO: here the default strategy of extra production.
            System.out.println("extraProduction");};
        else {
            defaultStrategy = () -> {};
            setStrategyDefault();
        }
        GUI.getGamePanel().repaint();
    }

    public void setStrategyFirst(){
        strategy = () -> {
            GUI.keepLeader(getID());
        };
    }

    public void setStrategyDefault(){
        strategy = defaultStrategy;
    }

    public void setMarketStrategy(){
        strategy = () -> GUI.getClient().send(new Strategy(ID));
    }

    public boolean isActivated(){
        return activated;
    }

}
