package it.polimi.ingsw.server.MessageToClient;

/**
 * Message to client: it notifies the player that is obliged to use a specified strategy.
 */
public class AskStrategy implements MessageToClient{
    private String type;

    public AskStrategy() {
        type = "AskStrategy";
    }
}
