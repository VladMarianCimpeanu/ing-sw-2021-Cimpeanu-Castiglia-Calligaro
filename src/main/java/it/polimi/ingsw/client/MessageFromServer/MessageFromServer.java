package it.polimi.ingsw.client.MessageFromServer;

import it.polimi.ingsw.client.Client;

/**
 * Message object received from server.
 */
public abstract class MessageFromServer {
    public abstract void activateMessage(Client client);
}
