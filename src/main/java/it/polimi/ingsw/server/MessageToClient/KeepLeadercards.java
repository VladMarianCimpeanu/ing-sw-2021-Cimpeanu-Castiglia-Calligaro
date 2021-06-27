package it.polimi.ingsw.server.MessageToClient;

import java.util.ArrayList;

/**
 * Message to client: it contains the IDs of leader cards given by the game to the client that receives this message.
 */
public class KeepLeadercards implements MessageToClient{
    private String type;
    private ArrayList<Integer> leaderIds;

    public KeepLeadercards(ArrayList<Integer> leaderIds) {
        this.type = "KeepLeadercards";
        this.leaderIds = leaderIds;
    }
}
