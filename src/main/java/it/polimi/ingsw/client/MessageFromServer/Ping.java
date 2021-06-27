package it.polimi.ingsw.client.MessageFromServer;

import it.polimi.ingsw.client.Client;

/**
 * Message from server: used by the server to verify that the connection is still alive.
 */
public class Ping extends MessageFromServer {

    @Override
    public void activateMessage(Client client) {
    }
}
