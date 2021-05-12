package it.polimi.ingsw.server.MessageToClient;

import java.util.ArrayList;

public class SelectedLeadercards implements MessageToClient {
    private String type;
    private ArrayList<Integer> leaderIds;

    public SelectedLeadercards(ArrayList<Integer> leaderIds) {
        this.type = "SelectedLeadercards";
        this.leaderIds = leaderIds;
    }
}
