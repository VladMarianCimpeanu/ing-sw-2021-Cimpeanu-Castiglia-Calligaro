package it.polimi.ingsw.client.MessageFromServer.Rejoin;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.MessageFromServer.MessageFromServer;
import it.polimi.ingsw.server.MessageToClient.MessageToClient;
import it.polimi.ingsw.client.Resource;

public class RejoinDepot extends MessageFromServer {
    private String player;
    private int shelf;
    private Resource resource;
    private int quantity;

    @Override
    public void activateMessage(Client client) {
        client.getGameView().getPlayer(player).getDepot().update(shelf, resource, quantity);
    }
}
