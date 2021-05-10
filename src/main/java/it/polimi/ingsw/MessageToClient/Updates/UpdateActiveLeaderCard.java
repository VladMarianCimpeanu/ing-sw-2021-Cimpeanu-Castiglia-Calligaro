package it.polimi.ingsw.MessageToClient.Updates;

import it.polimi.ingsw.MessageToClient.MessageToClient;

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
