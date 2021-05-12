package it.polimi.ingsw.server.MessageToClient;

public class SelectResourceOut implements MessageToClient{
    private String type;

    public SelectResourceOut() {
        type = "SelectResourceOut";
    }
}
