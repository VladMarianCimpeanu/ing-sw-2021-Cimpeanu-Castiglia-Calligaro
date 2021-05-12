package it.polimi.ingsw.client.MessageFromServer;

public class SelectResourceIn implements MessageToClient {
    private String type;

    public SelectResourceIn() {
        type = "SelectResourceIn";
    }
}
