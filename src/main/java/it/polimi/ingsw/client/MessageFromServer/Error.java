package it.polimi.ingsw.client.MessageFromServer;

import it.polimi.ingsw.client.Client;

/**
 * Message from server: it notifies the client that an error occurred. It contains a key word representing the error.
 */
public class Error extends MessageFromServer {
    private ErrorMessage error;


    @Override
    public void activateMessage(Client client) {
        client.getGameView().displayError(error);
    }
}
