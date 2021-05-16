package it.polimi.ingsw.client;

/**
 * color of development cards
 */
public enum Color {
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    GREEN("\u001B[32m");

    static final String RESET = "\u001B[0m";

    private String escape;

    Color(String escape) {
        this.escape = escape;
    }

    public String escape(){
        return escape;
    }

}
