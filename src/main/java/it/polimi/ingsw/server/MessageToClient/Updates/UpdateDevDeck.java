package it.polimi.ingsw.server.MessageToClient.Updates;

import it.polimi.ingsw.server.MessageToClient.MessageToClient;


/**
 * Message to client: it contains the ID of the development card just bought by the specified player and the position
 * in which has been placed.
 */
public class UpdateDevDeck implements MessageToClient {
    private String type;
    private String player;
    private int position;
    private int id;

    public UpdateDevDeck(String player, int position, int id) {
        this.type = "UpdateDevDeck";
        this.player = player;
        this.position = position;
        this.id = id;
    }
}
