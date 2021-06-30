package it.polimi.ingsw.client.MessageFromServer.Updates;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.MessageFromServer.MessageFromServer;
import it.polimi.ingsw.client.Resource;

/**
 * Message from server: it contains type and quantity of resources placed by a specified player in a specific
 * leader card (ID).
 */

public class UpdateExtraSlot extends MessageFromServer {
    private String player;
    private Resource resource;
    private int id;
    private int quantity;

    @Override
    public void activateMessage(Client client) {
        client.getGameView().getPlayer(player).dumpPlayer(client.getNickname(), "Extra Slot");
        client.getGameView().getPlayer(player).getLeaderCards().updateExtraSlot(id, resource, quantity);
    }
}
