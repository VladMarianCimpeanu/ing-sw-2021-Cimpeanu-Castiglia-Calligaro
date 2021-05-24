package it.polimi.ingsw.client;

public interface Clickable {

    boolean isClicked(int x, int y);
    //return((dimension.getX() <= x && x <= (dimension.getX() + dimension.getWidth())) && (dimension.getY() <= y && y <= (dimension.getY() + dimension.getHeight())));
    void click();

}
