package it.polimi.ingsw.server.MessageToClient;

public class GameStarted implements MessageToClient{
    private String type;

    public GameStarted() {
        type = "GameStarted";
    }
}
