package it.polimi.ingsw.server.MessageToClient;

/**
 * Message to client: it notifies the client that there are no waiting rooms available and he needs to set the size
 * of a new game.
 */
public class ModeRequest implements MessageToClient{
    private String type;

    public ModeRequest() {
        type = "ModeRequest";
    }
}
