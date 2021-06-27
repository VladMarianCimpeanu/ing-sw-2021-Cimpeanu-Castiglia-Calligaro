package it.polimi.ingsw.server.MessageToClient.Updates;

import it.polimi.ingsw.server.MessageToClient.MessageToClient;

import java.util.ArrayList;

/**
 * Message to client: it contains all the marbles inside the row that has just been changed and the new outer marble.
 */
public class UpdateMarketRow implements MessageToClient {
    private String type;
    private ArrayList<String> newRow;
    private String newOuterMarble;
    private int row;

    public UpdateMarketRow(ArrayList<String> newRow, String newOuterMarble, int row) {
        this.type = "UpdateMarketRow";
        this.newRow = newRow;
        this.newOuterMarble = newOuterMarble;
        this.row = row;
    }
}
