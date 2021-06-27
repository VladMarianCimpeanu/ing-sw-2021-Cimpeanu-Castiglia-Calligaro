package it.polimi.ingsw.client.MessageFromServer;

import it.polimi.ingsw.client.Client;

/**
 * Message from server: it contains the nickname of a player that has just joined the game.
 */
public class JoinPlayer extends MessageFromServer {
    private String player;

    @Override
    public void activateMessage(Client client) {
        client.getGameView().newWaitingPlayer(player);
    }
}
