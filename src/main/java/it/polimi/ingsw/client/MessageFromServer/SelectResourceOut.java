package it.polimi.ingsw.client.MessageFromServer;

import it.polimi.ingsw.client.Client;

public class SelectResourceOut extends MessageFromServer {
    @Override
    public void activateMessage(Client client) {
        System.out.println("Which resource do you want to craft?");
    }
}
