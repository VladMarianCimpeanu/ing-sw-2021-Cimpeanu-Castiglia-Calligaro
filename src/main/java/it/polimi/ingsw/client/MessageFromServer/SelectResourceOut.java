package it.polimi.ingsw.client.MessageFromServer;

public class SelectResourceOut implements MessageToClient {
    private String type;

    public SelectResourceOut() {
        type = "SelectResourceOut";
    }
}
