package it.polimi.ingsw.client.MessageFromServer;

import it.polimi.ingsw.client.Client;

public class ModeRequest extends MessageFromServer {

    @Override
    public void activateMessage(Client client) {
        System.out.println("choose number of players");
    }
}
