package it.polimi.ingsw.server.MessageToClient;

public class SelectResourceIn implements MessageToClient{
    private String type;

    public SelectResourceIn() {
        type = "SelectResourceIn";
    }
}
