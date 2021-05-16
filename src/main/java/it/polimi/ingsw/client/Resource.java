package it.polimi.ingsw.client;


public enum Resource {
    FAITH("\u001B[31m+"),
    COIN("\u001B[33m◉"),
    SHIELD("\u001B[34m▼"),
    STONE("\u001B[90m◆"),
    SERVANT("\u001B[95m■");
    static final String RESET = "\u001B[0m";

    private String escape;

    Resource(String escape) {
        this.escape = escape;
    }
    public String escape(){
        return escape;
    }
}
