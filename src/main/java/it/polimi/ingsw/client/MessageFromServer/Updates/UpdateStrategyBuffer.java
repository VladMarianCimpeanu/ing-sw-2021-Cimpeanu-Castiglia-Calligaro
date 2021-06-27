package it.polimi.ingsw.client.MessageFromServer.Updates;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.MessageFromServer.MessageFromServer;

/**
 * Message from server: it contains the IDs of the leader cards selected as strategies for the market. It contains the
 * number of white marbles that remain to be converted.
 */
public class UpdateStrategyBuffer extends MessageFromServer {
    int remaining;
    int id;
    @Override
    public void activateMessage(Client client) {
        client.getGameView().updatedUsedStrategies(remaining, id);
    }
}
