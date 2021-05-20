package it.polimi.ingsw.client.MessageFromServer;

import it.polimi.ingsw.client.Client;

public class ModeRequest extends MessageFromServer {

    @Override
    public void activateMessage(Client client) {
        client.getGameView().dumpMessage("There are no waiting rooms available: you are the host. Choose number of players");
    }
}
