package it.polimi.ingsw.client;

/**
 * marble of the market for the client.
 */
public enum Marble {
    RED("\u001B[31m@", "images/punchboard/marbles/red.png"),
    GREY("\u001B[90m@", "images/punchboard/marbles/grey.png"),
    YELLOW("\u001B[33m@", "images/punchboard/marbles/yellow.png"),
    BLUE("\u001B[34m@", "images/punchboard/marbles/blue.png"),
    PURPLE("\u001B[95m@", "images/punchboard/marbles/purple.png"),
    WHITE("@", "images/punchboard/marbles/white.png");
    static final String RESET = "\u001B[0m";

    private final String escape;
    private final String url;

    Marble(String escape, String url) {
        this.escape = escape;
        this.url = url;
    }

    /**
     * Return the representation of the marble as a string.
     * @return a string formed by two escape characters: the first one indicates the color of the marble, whereas
     * the second represent the symbol used to represent the marble.
     */
    public String escape(){
        return escape;
    }

    /**
     * gives the image of the marble.
     * @return the path of the pic representing the marble, starting from resources directory.
     */
    public String url(){
        return url;
    }

}