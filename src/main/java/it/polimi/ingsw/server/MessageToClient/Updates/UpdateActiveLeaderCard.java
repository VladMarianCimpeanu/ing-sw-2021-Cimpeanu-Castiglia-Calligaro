package it.polimi.ingsw.server.MessageToClient.Updates;

import it.polimi.ingsw.server.MessageToClient.MessageToClient;


/**
 * Message to the client: it notifies that a specified leader card has been activated by specific player.
 * It contains the ID of the leader card and the nickname of the player who performed the activation.
 */
public class UpdateActiveLeaderCard implements MessageToClient {
    private String type;
    private String player;
    private int id;

    public UpdateActiveLeaderCard(String player, int id) {
        this.type = "UpdateActiveLeaderCard";
        this.player = player;
        this.id = id;
    }
}
