package it.polimi.ingsw.server.MessageToClient.Updates;

import it.polimi.ingsw.server.MessageToClient.MessageToClient;

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
