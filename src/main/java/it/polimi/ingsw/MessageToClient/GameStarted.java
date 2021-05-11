package it.polimi.ingsw.MessageToClient;

public class GameStarted implements MessageToClient{
    private String type;

    public GameStarted() {
        type = "GameStarted";
    }
}
