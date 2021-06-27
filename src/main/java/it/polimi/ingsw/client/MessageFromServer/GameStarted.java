package it.polimi.ingsw.client.MessageFromServer;

import it.polimi.ingsw.client.Client;

/**
 * Message from server: the setup phase is ended.
 */
public class GameStarted extends MessageFromServer {

    @Override
    public void activateMessage(Client client) {
        client.getGameView().startGame();
    }
}
