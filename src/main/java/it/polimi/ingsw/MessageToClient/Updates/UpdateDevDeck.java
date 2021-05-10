package it.polimi.ingsw.MessageToClient.Updates;

import it.polimi.ingsw.MessageToClient.MessageToClient;

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
