package it.polimi.ingsw.client.MessageFromServer;

import it.polimi.ingsw.client.Client;

public class SelectResourceIn extends MessageFromServer {
    @Override
    public void activateMessage(Client client) {
        System.out.println("Which resources do you want to you use for base crafting?");
    }
}
