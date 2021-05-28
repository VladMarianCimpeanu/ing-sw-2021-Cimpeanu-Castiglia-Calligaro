package it.polimi.ingsw.client;

/**
 * object that can be clicked.
 */
public interface Clickable {
    /**
     * checks if the object is clicked.
     * @param x X coordinate of the click.
     * @param y Y coordinate of the click.
     * @return true if the object has been clicked.
     */
    boolean isClicked(int x, int y);

    /**
     * activate the object clicked.
     * @param x X coordinate of the click.
     * @param y Y coordinate of the click.
     */
    void click(int x, int y);

}
