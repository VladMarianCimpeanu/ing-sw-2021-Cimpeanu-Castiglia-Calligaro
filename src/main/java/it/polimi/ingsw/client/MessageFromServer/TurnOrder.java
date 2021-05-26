package it.polimi.ingsw.client.MessageFromServer;

import it.polimi.ingsw.client.Client;

public class TurnOrder extends MessageFromServer {
    private int position;
    private int numberOfResources;

    @Override
    public void activateMessage(Client client) {
        System.out.println("you are in position: " + position + "\nchoose " + numberOfResources +  " resources");
    }
}
