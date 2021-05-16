package it.polimi.ingsw.client;

/**
 * marble of the market
 */
public enum Marble {
    RED("\u001B[31m◉"),
    GREY("\u001B[90m◉"),
    YELLOW("\u001B[33m◉"),
    BLUE("\u001B[34m◉"),
    PURPLE("\u001B[95m◉"),
    WHITE("◉");
    static final String RESET = "\u001B[0m";

    private String escape;

    Marble(String escape) {
        this.escape = escape;
    }
    public String escape(){
        return escape;
    }

}