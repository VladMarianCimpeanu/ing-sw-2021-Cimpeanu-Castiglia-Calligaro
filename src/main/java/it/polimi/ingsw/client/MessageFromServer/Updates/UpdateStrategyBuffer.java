package it.polimi.ingsw.client.MessageFromServer.Updates;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.MessageFromServer.MessageFromServer;

public class UpdateStrategyBuffer extends MessageFromServer {
    int remaining;
    int id;
    @Override
    public void activateMessage(Client client) {
        client.getGameView().updatedUsedStrategies(remaining, id);
    }
}
