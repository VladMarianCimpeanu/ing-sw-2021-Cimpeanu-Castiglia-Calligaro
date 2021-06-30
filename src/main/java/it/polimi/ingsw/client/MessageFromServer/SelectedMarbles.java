package it.polimi.ingsw.client.MessageFromServer;

import it.polimi.ingsw.client.Client;

import java.util.ArrayList;

/**
 * Message from server: it contains marbles obtained by the market.
 */
public class SelectedMarbles extends MessageFromServer{
    private ArrayList<String> marbles;

    @Override
    public void activateMessage(Client client) {
        client.getGameView().dumpMessage("You have received:\n" + marbles);
    }
}
