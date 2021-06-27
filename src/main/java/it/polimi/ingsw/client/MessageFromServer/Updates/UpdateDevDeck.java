package it.polimi.ingsw.client.MessageFromServer.Updates;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.MessageFromServer.MessageFromServer;

/**
 * Message from server: it contains the ID of the development card just bought by the specified player and the position
 * in which has been placed.
 */

public class UpdateDevDeck extends MessageFromServer {
    private String player;
    private int position;
    private int id;

    @Override
    public void activateMessage(Client client) {
        client.getGameView().getPlayer(player).getDecks().addCard(position, id, player);
    }
}
