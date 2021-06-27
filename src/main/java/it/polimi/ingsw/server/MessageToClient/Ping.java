package it.polimi.ingsw.server.MessageToClient;

/**
 * Message to client: used by the server to verify that the connection is still alive.
 */
public class Ping implements MessageToClient{
    private String type;

    public Ping() {
        this.type = "Ping";
    }
}
