package it.polimi.ingsw.client.MessageFromServer;

import it.polimi.ingsw.client.Client;

/**
 * Message from server: it notifies the client that there are no waiting rooms available and he needs to set the size
 * of a new game.
 */
public class ModeRequest extends MessageFromServer {

    @Override
    public void activateMessage(Client client) {
        client.getGameView().requireMode();
    }
}
