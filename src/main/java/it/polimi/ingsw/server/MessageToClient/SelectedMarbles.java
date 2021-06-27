package it.polimi.ingsw.server.MessageToClient;

import java.util.ArrayList;

/**
 * Message to client: it contains marbles obtained by the market.
 */
public class SelectedMarbles implements MessageToClient{
    private String type;
    private ArrayList<String> marbles;

    public SelectedMarbles(ArrayList<String> marbles) {
        this.type = "SelectedMarbles";
        this.marbles = marbles;
    }
}
