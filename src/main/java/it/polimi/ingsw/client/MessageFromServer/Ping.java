package it.polimi.ingsw.client.MessageFromServer;

public class Ping implements MessageToClient {
    private String type;

    public Ping() {
        this.type = "Ping";
    }
}
