package it.polimi.ingsw.client.MessageFromServer;

import it.polimi.ingsw.client.Client;

/**
 * Message from server: it contains the nickname of the player that has just crashed.
 */
public class CrashedPlayer extends MessageFromServer {
    private String nickname;

    @Override
    public void activateMessage(Client client) {
        client.getGameView().crashedWaitingPlayer(nickname);
    }
}
