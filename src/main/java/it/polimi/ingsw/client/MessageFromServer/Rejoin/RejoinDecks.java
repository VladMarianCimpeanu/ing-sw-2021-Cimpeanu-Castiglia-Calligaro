package it.polimi.ingsw.client.MessageFromServer.Rejoin;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.MessageFromServer.MessageFromServer;

/**
 * Message containing all the development cards that can be activated by a specified player.
 * This message is received from the server when the player tries to join an already started game.
 */
public class RejoinDecks extends MessageFromServer {
    private String player;
    private int position;
    private int id;

    @Override
    public void activateMessage(Client client) {
        client.getGameView().getPlayer(player).getDecks().reloadCard(position, id, player);
    }
}
