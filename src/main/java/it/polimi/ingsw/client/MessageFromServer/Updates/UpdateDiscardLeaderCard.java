package it.polimi.ingsw.client.MessageFromServer.Updates;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.MessageFromServer.MessageFromServer;

/**
 * Message from server: it contains the ID of the leader card that has just been discarded by a specified player.
 */

public class UpdateDiscardLeaderCard extends MessageFromServer {
    private String player;
    private int id;

    @Override
    public void activateMessage(Client client) {
        client.getGameView().getPlayer(client.getNickname()).dumpPlayer(player, "LeaderCards");
        client.getGameView().getPlayer(player).getLeaderCards().dumpMessage("Discarded a LeaderCard.");

        if(client.getNickname().equals(player)) {
            client.getGameView().getPlayer(player).getLeaderCards().remove(id);
            client.getGameView().getPlayer(player).getLeaderCards().show();
        }
    }
}
