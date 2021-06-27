package it.polimi.ingsw.client.MessageFromServer.Rejoin;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.MessageFromServer.MessageFromServer;
import it.polimi.ingsw.client.Resource;

/**
 * Message containing all the resources owned by a specified player in his extra slots.
 * This message is received from the server when the player tries to join an already started game.
 */
public class RejoinExtraSlot extends MessageFromServer {
    private String player;
    private Resource resource;
    private int id;
    private int quantity;

    @Override
    public void activateMessage(Client client) {
    }
}
