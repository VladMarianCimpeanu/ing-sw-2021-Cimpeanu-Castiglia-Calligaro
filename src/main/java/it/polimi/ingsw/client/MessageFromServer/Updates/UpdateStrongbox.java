package it.polimi.ingsw.client.MessageFromServer.Updates;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.MessageFromServer.MessageFromServer;
import it.polimi.ingsw.client.modelLight.PlayerView;
import it.polimi.ingsw.client.Resource;

import java.util.Map;

/**
 * Message from server: contains all the resources owned by a specified player in his strongbox.
 */
public class UpdateStrongbox extends MessageFromServer {
    private String player;
    private Map<Resource, Integer> deltaResources;

    @Override
    public void activateMessage(Client client) {
        PlayerView p = client.getGameView().getPlayer(client.getNickname());
        p.dumpPlayer(player, "Strongbox");
        client.getGameView().getPlayer(player).getStrongbox().update(deltaResources);
        //client.getGameView().getPlayer(player).getStrongbox().show();
    }
}
