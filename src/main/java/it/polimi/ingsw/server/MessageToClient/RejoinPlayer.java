package it.polimi.ingsw.server.MessageToClient;

public class RejoinPlayer implements MessageToClient{
    private String type;
    private String player;

    public RejoinPlayer(String player) {
        this.type = "RejoinPlayer";
        this.player = player;
    }
}
