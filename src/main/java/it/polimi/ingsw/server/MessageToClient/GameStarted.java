package it.polimi.ingsw.server.MessageToClient;

/**
 * Message to client: the setup phase is ended.
 */
public class GameStarted implements MessageToClient{
    private String type;

    public GameStarted() {
        type = "GameStarted";
    }
}
