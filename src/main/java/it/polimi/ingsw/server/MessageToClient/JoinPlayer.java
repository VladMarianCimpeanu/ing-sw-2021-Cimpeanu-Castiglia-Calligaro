package it.polimi.ingsw.server.MessageToClient;

public class JoinPlayer implements MessageToClient{
    private String type;
    private String player;

    public JoinPlayer(String player) {
        type = "JoinPlayer";
        this.player = player;
    }
}
