package it.polimi.ingsw.server.MessageToClient;

import java.util.ArrayList;

public class SelectedMarbles implements MessageToClient{
    private String type;
    private ArrayList<String> marbles;

    public SelectedMarbles(ArrayList<String> marbles) {
        this.type = "SelectedMarbles";
        this.marbles = marbles;
    }
}
