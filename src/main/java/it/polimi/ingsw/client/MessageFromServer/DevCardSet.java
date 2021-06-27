package it.polimi.ingsw.client.MessageFromServer;

import it.polimi.ingsw.client.Client;

import java.util.ArrayList;

/**
 * Message from server containing all the development cards that can be bought. This message is sent only during the setup
 * game phase.
 */
public class DevCardSet extends MessageFromServer {
    private ArrayList<ArrayList<Integer>> set;

    @Override
    public void activateMessage(Client client) {
        client.getGameView().getCards().setDecks(set);
    }
}
