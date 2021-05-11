package it.polimi.ingsw.MessageToClient;

public class Ping implements MessageToClient{
    private String type;

    public Ping() {
        this.type = "Ping";
    }
}
