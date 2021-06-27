package it.polimi.ingsw.server.MessageToClient;

/**
 * Message to client: it notifies the client that he can select the input resources for base & extra production.
 */
public class SelectResourceIn implements MessageToClient{
    private String type;

    public SelectResourceIn() {
        type = "SelectResourceIn";
    }
}
