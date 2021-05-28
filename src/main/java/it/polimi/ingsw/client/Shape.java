package it.polimi.ingsw.client;


/**
 * this class contains all the information of a displayable object.
 */
public class Shape {
    private int x;
    private int y;
    private int width;
    private int height;

    public Shape(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

//    public boolean isClicked(int x, int y){
//        return((this.x <= x && x <= (this.x + this.width)) && (this.y <= y && y <= (this.y + this.height)));
//    }

    /**
     * get the X coordinate of the top left corner of the image.
     * @return an integer representing the X coordinate of the top left corner of the image.
     */
    public int getX() {
        return x;
    }

    /**
     * get the Y coordinate of the top left corner of the image.
     * @return an integer representing the Y coordinate of the top left corner of the image.
     */
    public int getY() {
        return y;
    }

    /**
     * get the width of the image
     * @return an integer representing the width of the image
     */
    public int getWidth() {
        return width;
    }

    /**
     * get the height of the image
     * @return an integer representing the height of the image
     */
    public int getHeight() {
        return height;
    }
}
