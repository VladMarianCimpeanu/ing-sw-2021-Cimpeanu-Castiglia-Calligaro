package it.polimi.ingsw.server.MessageToClient;

/**
 * Message to client: it contains the nickname of the current player.
 */
public class ItsYourTurn implements MessageToClient{
    private String type;
    private String player;

    public ItsYourTurn(String player) {
        type = "ItsYourTurn";
        this.player = player;
    }
}
