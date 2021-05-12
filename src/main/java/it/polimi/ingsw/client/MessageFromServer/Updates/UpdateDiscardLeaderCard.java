package it.polimi.ingsw.client.MessageFromServer.Updates;

import it.polimi.ingsw.client.MessageFromServer.MessageToClient;

public class UpdateDiscardLeaderCard implements MessageToClient {
    private final String type;
    private String player;
    private int id;

    public UpdateDiscardLeaderCard(String player, int id) {
        type = "UpdateDiscardLeaderCard";
        this.player = player;
        this.id = id;
    }
}
