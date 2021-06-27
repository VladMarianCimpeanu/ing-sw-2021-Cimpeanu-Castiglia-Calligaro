package it.polimi.ingsw.server.MessageToClient;

/**
 * Message to client: it contains the nickname of the player that has just rejoined the game.
 */
public class RejoinPlayer implements MessageToClient{
    private String type;
    private String player;

    public RejoinPlayer(String player) {
        this.type = "RejoinPlayer";
        this.player = player;
    }
}
