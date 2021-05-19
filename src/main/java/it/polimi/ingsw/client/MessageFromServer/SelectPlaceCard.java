package it.polimi.ingsw.client.MessageFromServer;

import it.polimi.ingsw.client.Client;

public class SelectPlaceCard extends MessageFromServer {

    @Override
    public void activateMessage(Client client) {
        client.getGameView().dumpMessage("Where do you want to place the development card?");
    }
}
