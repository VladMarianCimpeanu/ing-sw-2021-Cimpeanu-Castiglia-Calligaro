package it.polimi.ingsw.client.MessageFromServer.Rejoin;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.MessageFromServer.MessageFromServer;
import it.polimi.ingsw.client.Resource;

import java.util.Map;

/**
 * Message containing all the resources owned by a specified player in his strongbox.
 * This message is received from the server when the player tries to join an already started game.
 */

public class RejoinStrongbox extends MessageFromServer {
    private String player;
    private Map<Resource, Integer> deltaResources;

    @Override
    public void activateMessage(Client client) {
        client.getGameView().getPlayer(player).getStrongbox().update(deltaResources);
    }
}
