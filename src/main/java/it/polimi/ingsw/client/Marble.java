package it.polimi.ingsw.client;

/**
 * marble of the market for the client.
 */
public enum Marble {
    RED("\u001B[31m@"),
    GREY("\u001B[90m@"),
    YELLOW("\u001B[33m@"),
    BLUE("\u001B[34m@"),
    PURPLE("\u001B[95m@"),
    WHITE("@");
    static final String RESET = "\u001B[0m";

    private final String escape;

    Marble(String escape) {
        this.escape = escape;
    }

    /**
     * Return the representation of the marble as a string.
     * @return a string formed by two escape characters: the first one indicates the color of the marble, whereas
     * the second represent the symbol used to represent the marble.
     */
    public String escape(){
        return escape;
    }

}