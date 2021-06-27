package it.polimi.ingsw.client.MessageFromServer.Updates;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.MessageFromServer.MessageFromServer;

import java.util.ArrayList;

/**
 * Message from the server: it notifies that a specified leader card has been activated by specific player.
 * It contains the ID of the leader card and the nickname of the player who performed the activation.
 */

public class UpdateActiveLeaderCard extends MessageFromServer {
    private String player;
    private int id;

    @Override
    public void activateMessage(Client client) {
        client.getGameView().getPlayer(client.getNickname()).dumpPlayer(player, "LeaderCards");
        client.getGameView().getPlayer(player).getLeaderCards().dumpMessage("Activated a LeaderCard.");

        if(client.getNickname().equals(player)) {
            client.getGameView().getPlayer(player).getLeaderCards().activate(id);
            client.getGameView().getPlayer(player).getLeaderCards().show();
        }
        else{
            ArrayList<Integer> idS = new ArrayList<>();
            idS.add(id);
            client.getGameView().getPlayer(player).getLeaderCards().update(idS);
            client.getGameView().getPlayer(player).getLeaderCards().activate(id);
        }
    }
}
