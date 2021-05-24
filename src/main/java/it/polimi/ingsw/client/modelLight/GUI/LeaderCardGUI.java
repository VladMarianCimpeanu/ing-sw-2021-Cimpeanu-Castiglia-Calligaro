package it.polimi.ingsw.client.modelLight.GUI;

import it.polimi.ingsw.client.Clickable;
import it.polimi.ingsw.client.Shape;
import it.polimi.ingsw.client.modelLight.LeaderCardView;

public class LeaderCardGUI extends LeaderCardView implements Clickable {

    private int id;
    private String url;
    private Shape shape;

    public LeaderCardGUI() {
        shape = new Shape(0,0,1000,1000);
    }

    @Override
    public void show() {

    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public boolean isClicked(int x, int y) {
        return((shape.getX() <= x && x <= (shape.getX() + shape.getWidth())) && (shape.getY() <= y && y <= (shape.getY() + shape.getHeight())));
    }

    @Override
    public void click() {
        System.out.println("sono stato cliccato");
    }

    public Shape getShape() {
        return shape;
    }

    public String getUrl() {
        return url;
    }
}
