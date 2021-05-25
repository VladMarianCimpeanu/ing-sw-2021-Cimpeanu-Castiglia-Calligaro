package it.polimi.ingsw.client.MessageFromServer;

import it.polimi.ingsw.client.Client;

public class Error extends MessageFromServer {
    private ErrorMessage error;


    @Override
    public void activateMessage(Client client) {
        client.getGameView().displayError(error);
    }
}
