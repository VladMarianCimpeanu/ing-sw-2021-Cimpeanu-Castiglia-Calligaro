package it.polimi.ingsw.server.MessageToClient;

/**
 * Message to client: it contains the nickname of the player that has just crashed.
 */
public class CrashedPlayer implements MessageToClient{
    private String type;
    private String nickname;

    public CrashedPlayer(String nickname) {
        this.type = "CrashedPlayer";
        this.nickname = nickname;
    }
}
