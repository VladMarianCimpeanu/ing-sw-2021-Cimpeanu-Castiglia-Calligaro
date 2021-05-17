package it.polimi.ingsw.client.MessageFromServer.Updates;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.MessageFromServer.MessageFromServer;
import it.polimi.ingsw.server.model.benefit.Resource;

public class UpdateWarehouseDepot extends MessageFromServer {
    private String player;
    private int shelf;
    private Resource resource;
    private int quantity;

    @Override
    public void activateMessage(Client client) {
        //TODO
    }
}
