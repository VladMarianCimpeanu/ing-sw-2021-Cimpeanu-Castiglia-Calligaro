package it.polimi.ingsw.client.MessageFromServer.Updates;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.MessageFromServer.MessageFromServer;

import java.util.Map;

public class UpdateFaithpath extends MessageFromServer {
    private Map<String, Integer> newPositions;

    @Override
    public void activateMessage(Client client) {
        if(newPositions != null) client.getGameView().getFaithPathView().update(newPositions);
    }
}
