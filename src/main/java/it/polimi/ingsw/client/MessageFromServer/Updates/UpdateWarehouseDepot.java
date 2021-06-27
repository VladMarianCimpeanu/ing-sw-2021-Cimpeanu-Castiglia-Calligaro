package it.polimi.ingsw.client.MessageFromServer.Updates;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.MessageFromServer.MessageFromServer;
import it.polimi.ingsw.client.Resource;


/**
 * Message from server: it contains the shelf of a specified player that has been changed. It indicates the number
 * and which kind of resources are placed in the specified shelf.
 */
public class UpdateWarehouseDepot extends MessageFromServer {
    private String player;
    private int shelf;
    private Resource resource;
    private int quantity;

    @Override
    public void activateMessage(Client client) {
        client.getGameView().getPlayer(player).getDepot().update(shelf, resource, quantity);
        client.getGameView().getPlayer(player).dumpPlayer(client.getNickname(), "WarehouseDepot");
        client.getGameView().getPlayer(player).getDepot().showUpdate();
    }
}
