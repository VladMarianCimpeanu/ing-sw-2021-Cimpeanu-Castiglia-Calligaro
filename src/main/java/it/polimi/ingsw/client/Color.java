package it.polimi.ingsw.client;

/**
 * color of development cards
 */
public enum Color {
    GREEN("\u001B[32m",0),
    BLUE("\u001B[34m", 1),
    YELLOW("\u001B[33m", 2),
    PURPLE("\u001B[95m", 3);

    public static final String RESET = "\u001B[0m";

    private final String escape;
    private final int index;

    Color(String escape, int index) {
        this.escape = escape;
        this.index = index;
    }

    public String escape(){
        return escape;
    }

    public int getIndex(){return index;}

}
