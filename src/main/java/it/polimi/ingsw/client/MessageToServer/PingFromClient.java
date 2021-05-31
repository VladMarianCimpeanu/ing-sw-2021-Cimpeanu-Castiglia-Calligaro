package it.polimi.ingsw.client.MessageToServer;

public class PingFromClient implements MessageToServer{
    private String type;

    public PingFromClient(){
        type = "PingFromClient";
    }
}
