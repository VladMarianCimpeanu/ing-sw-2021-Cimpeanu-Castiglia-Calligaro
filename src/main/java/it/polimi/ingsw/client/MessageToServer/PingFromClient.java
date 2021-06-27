package it.polimi.ingsw.client.MessageToServer;

/**
 * Ping to server used to check if the connection is still alive with the server.
 */
public class PingFromClient implements MessageToServer{
    private String type;

    public PingFromClient(){
        type = "PingFromClient";
    }
}
