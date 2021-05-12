package it.polimi.ingsw.client.MessageFromServer.Updates;

import it.polimi.ingsw.client.MessageFromServer.MessageToClient;

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
