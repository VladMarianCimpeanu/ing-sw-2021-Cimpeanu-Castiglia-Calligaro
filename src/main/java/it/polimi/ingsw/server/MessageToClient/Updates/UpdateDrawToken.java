package it.polimi.ingsw.server.MessageToClient.Updates;

import it.polimi.ingsw.server.MessageToClient.MessageToClient;

/**
 * Message to client: it contains the ID of the action token which has just been drawn.
 */
public class UpdateDrawToken implements MessageToClient {
    private String type;
    private int id;

    public UpdateDrawToken(int id) {
        this.type = "UpdateDrawToken";
        this.id = id;
    }
}
