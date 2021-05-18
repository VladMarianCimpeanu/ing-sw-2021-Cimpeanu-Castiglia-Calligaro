package it.polimi.ingsw.server.MessageToClient.Updates;

import it.polimi.ingsw.server.MessageToClient.MessageToClient;

public class UpdateDrawToken implements MessageToClient {
    private String type;
    private int id;

    public UpdateDrawToken(int id) {
        this.type = "UpdateDrawToken";
        this.id = id;
    }
}
