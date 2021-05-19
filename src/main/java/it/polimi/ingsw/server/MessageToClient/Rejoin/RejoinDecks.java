package it.polimi.ingsw.server.MessageToClient.Rejoin;

import it.polimi.ingsw.server.MessageToClient.MessageToClient;

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
