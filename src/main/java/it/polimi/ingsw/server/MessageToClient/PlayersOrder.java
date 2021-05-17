package it.polimi.ingsw.server.MessageToClient;

import java.util.ArrayList;

public class PlayersOrder implements MessageToClient{
    private String type;
    private ArrayList<String> nicknames;

    public PlayersOrder(ArrayList<String> nicknames) {
        this.type = "PlayersOrder";
        this.nicknames = nicknames;
    }
}
