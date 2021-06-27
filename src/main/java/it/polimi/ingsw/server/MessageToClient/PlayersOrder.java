package it.polimi.ingsw.server.MessageToClient;

import java.util.ArrayList;

/**
 * Message to client: it contains the a list containing the nicknames of the players that are playing the same game of the
 * client, ordered by the turn order.
 */
public class PlayersOrder implements MessageToClient{
    private String type;
    private ArrayList<String> nicknames;

    public PlayersOrder(ArrayList<String> nicknames) {
        this.type = "PlayersOrder";
        this.nicknames = nicknames;
    }
}
