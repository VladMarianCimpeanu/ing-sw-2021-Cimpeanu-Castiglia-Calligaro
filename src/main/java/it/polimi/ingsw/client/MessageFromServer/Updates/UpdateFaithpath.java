package it.polimi.ingsw.client.MessageFromServer.Updates;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.MessageFromServer.MessageFromServer;

import java.util.Map;

/**
 * Message from server: contains all the faithpath's positions after the last update.
 * If the game is in single player mode, black cross is indicated with 'blackCross' nickname.
 */
public class UpdateFaithpath extends MessageFromServer {
    private Map<String, Integer> newPositions;

    @Override
    public void activateMessage(Client client) {
        if(newPositions != null) client.getGameView().getFaithPathView().update(newPositions);
    }
}
