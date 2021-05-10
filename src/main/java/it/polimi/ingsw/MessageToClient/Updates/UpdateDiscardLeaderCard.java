package it.polimi.ingsw.MessageToClient.Updates;

import it.polimi.ingsw.MessageToClient.MessageToClient;

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
