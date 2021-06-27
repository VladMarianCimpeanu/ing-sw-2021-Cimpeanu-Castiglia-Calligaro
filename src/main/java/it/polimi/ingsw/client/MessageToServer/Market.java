package it.polimi.ingsw.client.MessageToServer;

/**
 * Message to server: it contains the row/ column of the market selected by the client.
 */
public class Market implements MessageToServer {
    private String type;
    private String direction;
    private int position;

    public Market(String direction, int position) {
        this.type = "Market";
        this.direction = direction;
        this.position = position;
    }
}
