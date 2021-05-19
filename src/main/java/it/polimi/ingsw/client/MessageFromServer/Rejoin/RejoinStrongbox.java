package it.polimi.ingsw.client.MessageFromServer.Rejoin;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.MessageFromServer.MessageFromServer;
import it.polimi.ingsw.client.Resource;

import java.util.Map;

public class RejoinStrongbox extends MessageFromServer {
    private String player;
    private Map<Resource, Integer> deltaResources;

    @Override
    public void activateMessage(Client client) {
        client.getGameView().getPlayer(player).getStrongbox().update(deltaResources);
    }
}
