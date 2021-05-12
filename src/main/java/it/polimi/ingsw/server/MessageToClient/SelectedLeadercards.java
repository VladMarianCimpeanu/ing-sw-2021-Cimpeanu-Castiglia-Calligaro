package it.polimi.ingsw.server.MessageToClient;

import java.util.ArrayList;

public class SelectedLeadercards implements MessageToClient {
    private String type;
    private int id1;
    private int id2;

    public SelectedLeadercards(int id1, int id2) {
        this.type = "SelectedLeadercards";
        this.id1 = id1;
        this.id2 = id2;
    }
}
