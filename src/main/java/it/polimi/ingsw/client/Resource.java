package it.polimi.ingsw.client;

//TODO: ask if these escape characters associated with unicode characters will behave in the same way
// in different platforms

public enum Resource {
    FAITH("\u001B[31m+"),
    COIN("\u001B[33m\u25CF"),
    SHIELD("\u001B[34m\u25BC"),
    STONE("\u001B[90m\u25C6"),
    SERVANT("\u001B[95m\u25A0");
    static final String RESET = "\u001B[0m";

    private final String escape;

    Resource(String escape) {
        this.escape = escape;
    }

    public String escape(){
        return escape;
    }
}
