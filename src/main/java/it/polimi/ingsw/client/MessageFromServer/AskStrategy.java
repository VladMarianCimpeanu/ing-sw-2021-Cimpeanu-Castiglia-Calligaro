package it.polimi.ingsw.client.MessageFromServer;

import it.polimi.ingsw.client.Client;

public class AskStrategy extends MessageFromServer {
    @Override
    public void activateMessage(Client client) {
        System.out.println("Insert strategies");
    }
}
