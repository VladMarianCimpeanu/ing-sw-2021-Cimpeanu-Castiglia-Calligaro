package it.polimi.ingsw.server.MessageToClient.Rejoin;

import it.polimi.ingsw.server.MessageToClient.MessageToClient;

/**
 * Message containing all the development cards that can be activated by a specified player.
 * This message is sent to the client when the player tries to join an already started game.
 */
public class RejoinDecks implements MessageToClient {
    private String type;
    private String player;
    private int position;
    private int id;

    public RejoinDecks(String player, int position, int id) {
        this.type = "RejoinDecks";
        this.player = player;
        this.position = position;
        this.id = id;
    }
}
