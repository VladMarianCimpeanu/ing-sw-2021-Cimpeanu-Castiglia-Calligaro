package it.polimi.ingsw.MessageToClient;

public class JoinPlayer implements MessageToClient{
    private String type;
    private String player;

    public JoinPlayer(String player) {
        type = "JoinPlayer";
        this.player = player;
    }
}
