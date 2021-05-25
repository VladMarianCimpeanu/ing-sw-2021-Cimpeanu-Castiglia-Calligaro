package it.polimi.ingsw.client;

/**
 * color of development cards for the view.
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

    /**
     * @return Return the escape character of the specified color.
     */
    public String escape(){
        return escape;
    }

    /**
     * @return given a specified color, it returns the corresponding position on the grid of development cards that can
     * be purchased.
     */
    public int getIndex(){return index;}
}
