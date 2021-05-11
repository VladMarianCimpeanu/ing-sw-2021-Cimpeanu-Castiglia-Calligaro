package it.polimi.ingsw.MessageToClient;

public class SelectResourceIn implements MessageToClient{
    private String type;

    public SelectResourceIn() {
        type = "SelectResourceIn";
    }
}
