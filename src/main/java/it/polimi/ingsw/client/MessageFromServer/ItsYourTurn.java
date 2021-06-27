package it.polimi.ingsw.client.MessageFromServer;


import it.polimi.ingsw.client.Client;

/**
 * Message from server: it contains the nickname of the current player.
 */
public class ItsYourTurn extends MessageFromServer {
    private String player;

    @Override
    public void activateMessage(Client client) {
        client.getGameView().changeTurn(player);
    }
}
