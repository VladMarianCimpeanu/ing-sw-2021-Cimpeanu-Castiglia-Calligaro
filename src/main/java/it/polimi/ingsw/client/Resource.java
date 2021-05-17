package it.polimi.ingsw.client;

//TODO: ask if these escape characters associated with unicode characters will behave in the same way
// in different platforms

/**
 * enum representing resources to the client.
 */
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

    /**
     * Return the representation of the resource as a string.
     * @return a string formed by two escape characters: the first one indicates the color of the resource, whereas
     * the second represent the symbol used to represent the resource.
     */
    public String escape(){
        return escape;
    }
}
