package it.polimi.ingsw.server.MessageToClient;

/**
 * Message to client: it notifies the client that he can select the output resources for the base production.
 */
public class SelectResourceOut implements MessageToClient{
    private String type;

    public SelectResourceOut() {
        type = "SelectResourceOut";
    }
}
