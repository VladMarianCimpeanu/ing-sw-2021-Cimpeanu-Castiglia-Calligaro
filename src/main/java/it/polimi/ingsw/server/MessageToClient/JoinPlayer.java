package it.polimi.ingsw.server.MessageToClient;

/**
 * Message to client: it contains the nickname of a player that has just joined the game.
 */
public class JoinPlayer implements MessageToClient{
    private String type;
    private String player;

    public JoinPlayer(String player) {
        type = "JoinPlayer";
        this.player = player;
    }
}
