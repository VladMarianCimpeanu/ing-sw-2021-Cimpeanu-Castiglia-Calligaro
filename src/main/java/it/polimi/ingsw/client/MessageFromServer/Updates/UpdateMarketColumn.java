package it.polimi.ingsw.client.MessageFromServer.Updates;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.Marble;
import it.polimi.ingsw.client.MessageFromServer.MessageFromServer;

import java.util.ArrayList;

public class UpdateMarketColumn extends MessageFromServer {
    private ArrayList<String> newColumn;
    private String newOuterMarble;
    private int col;

    @Override
    public void activateMessage(Client client) {
        Marble[] content = new Marble[3];
        for(int i = 0; i<3; i++)
            content[i] = Marble.valueOf(newColumn.get(i));
        client.getGameView().getMarket().updateColumn(col, content, Marble.valueOf(newOuterMarble));
    }
}
