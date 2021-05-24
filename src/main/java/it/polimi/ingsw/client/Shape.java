package it.polimi.ingsw.client;

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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
