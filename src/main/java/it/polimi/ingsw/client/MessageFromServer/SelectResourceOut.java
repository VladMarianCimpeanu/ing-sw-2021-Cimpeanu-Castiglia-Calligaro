package it.polimi.ingsw.client.MessageFromServer;

import it.polimi.ingsw.client.Client;

public class SelectResourceOut extends MessageFromServer {
    @Override
    public void activateMessage(Client client) {
        client.getGameView().dumpMessage("Which resource do you want to craft?");
    }
}
