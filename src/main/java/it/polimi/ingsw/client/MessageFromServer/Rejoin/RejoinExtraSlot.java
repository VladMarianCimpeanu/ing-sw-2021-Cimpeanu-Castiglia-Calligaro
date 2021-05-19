package it.polimi.ingsw.client.MessageFromServer.Rejoin;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.MessageFromServer.MessageFromServer;
import it.polimi.ingsw.client.Resource;

public class RejoinExtraSlot extends MessageFromServer {
    private String player;
    private Resource resource;
    private int id;
    private int quantity;

    @Override
    public void activateMessage(Client client) {
    }
}
