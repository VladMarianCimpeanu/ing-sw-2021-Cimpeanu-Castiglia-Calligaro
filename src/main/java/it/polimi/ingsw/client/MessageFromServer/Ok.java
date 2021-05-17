package it.polimi.ingsw.client.MessageFromServer;

import it.polimi.ingsw.client.Client;

public class Ok extends MessageFromServer {
    private String message;

    @Override
    public void activateMessage(Client client) {
        System.out.println("ok: " + message);
    }
}
