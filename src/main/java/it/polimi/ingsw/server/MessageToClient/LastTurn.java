package it.polimi.ingsw.server.MessageToClient;

/**
 * Message to client: it notifies that a specified player has triggered the last turn due to a specific reason.
 */
public class LastTurn implements MessageToClient{
    private String type;
    private String player;
    private String cause;

    public LastTurn(String player, String cause) {
        type = "LastTurn";
        this.player = player;
        this.cause = cause;
    }
}
