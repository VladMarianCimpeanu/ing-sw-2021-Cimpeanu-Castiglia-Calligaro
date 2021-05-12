package it.polimi.ingsw.server.MessageToClient.Updates;

import it.polimi.ingsw.server.MessageToClient.MessageToClient;

import java.util.ArrayList;

public class UpdateMarketColumn implements MessageToClient {
    private String type;
    private ArrayList<String> newColumn;
    private String newOuterMarble;
    private int col;

    public UpdateMarketColumn(ArrayList<String> newColumn, String newOuterMarble, int col) {
        this.type = "UpdateMarketColumn";
        this.newColumn = newColumn;
        this.newOuterMarble = newOuterMarble;
        this.col = col;
    }
}
