package it.polimi.ingsw.client.modelLight.GUI;

import it.polimi.ingsw.client.Clickable;
import it.polimi.ingsw.client.Shape;
import it.polimi.ingsw.client.modelLight.DevelopmentCardView;

public class DevelopmentCardGUI extends DevelopmentCardView implements Clickable {
    private Shape shape;
    private String url;
    private int ID;
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
            System.out.println("click: buy");
        };
    }

    public void setToProduction(){
        actionOnClick = () -> {
            System.out.println("click: produce");
        };
    }

    public void setToReplaceable(){
        actionOnClick = () -> {
            System.out.println("click: replace");
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
