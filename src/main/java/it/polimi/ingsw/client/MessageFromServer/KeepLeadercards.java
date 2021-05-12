package it.polimi.ingsw.client.MessageFromServer;

import java.util.ArrayList;

public class KeepLeadercards implements MessageToClient {
    private String type;
    private ArrayList<Integer> leaderIds;

    public KeepLeadercards(ArrayList<Integer> leaderIds) {
        this.type = "KeepLeadercards";
        this.leaderIds = leaderIds;
    }
}
