package it.polimi.ingsw.client;

/**
 * marble of the market
 */
public enum Marble {
    RED("\u001B[31m\u25BC"),
    GREY("\u001B[90m\u25BC"),
    YELLOW("\u001B[33m\u25BC"),
    BLUE("\u001B[34m\u25BC"),
    PURPLE("\u001B[95m\u25BC"),
    WHITE("\u25BC");
    static final String RESET = "\u001B[0m";

    private final String escape;

    Marble(String escape) {
        this.escape = escape;
    }
    public String escape(){
        return escape;
    }

}