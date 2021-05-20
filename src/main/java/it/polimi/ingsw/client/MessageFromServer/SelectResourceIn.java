package it.polimi.ingsw.client.MessageFromServer;

import it.polimi.ingsw.client.Client;

public class SelectResourceIn extends MessageFromServer {
    @Override
    public void activateMessage(Client client) {
        client.getGameView().dumpMessage("Which resources do you want to use for base crafting?");
    }
}
