package it.polimi.ingsw.client.MessageFromServer.Updates;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.Marble;
import it.polimi.ingsw.client.MessageFromServer.MessageFromServer;

import java.util.ArrayList;


/**
 * Message from server: it contains all the marbles inside the row that has just been changed and the new outer marble.
 */
public class UpdateMarketRow extends MessageFromServer {
    private ArrayList<String> newRow;
    private String newOuterMarble;
    private int row;

    @Override
    public void activateMessage(Client client) {
        Marble[] content = new Marble[4];
        for(int i = 0; i<4; i++)
            content[i] = Marble.valueOf(newRow.get(i));
        client.getGameView().getMarket().updateRow(row, content, Marble.valueOf(newOuterMarble));
    }
}
