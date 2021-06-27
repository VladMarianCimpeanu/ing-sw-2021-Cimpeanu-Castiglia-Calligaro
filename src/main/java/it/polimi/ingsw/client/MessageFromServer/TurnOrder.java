package it.polimi.ingsw.client.MessageFromServer;

import it.polimi.ingsw.client.Client;

//TODO: players order?
public class TurnOrder extends MessageFromServer {
    private int position;
    private int numberOfResources;

    @Override
    public void activateMessage(Client client) {
        client.getGameView().chooseResources(position, numberOfResources);
    }
}
