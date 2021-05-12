package it.polimi.ingsw.client.MessageFromServer;

public class GameStarted implements MessageToClient {
    private String type;

    public GameStarted() {
        type = "GameStarted";
    }
}
