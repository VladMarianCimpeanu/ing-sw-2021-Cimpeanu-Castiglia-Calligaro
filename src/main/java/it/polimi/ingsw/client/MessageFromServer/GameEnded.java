package it.polimi.ingsw.client.MessageFromServer;

import it.polimi.ingsw.client.Client;

import java.util.Map;

/**
 * Message from server: it notifies that the game is ended.
 * It contains the points earned by each player and the number of resources owned by each player.
 */
public class GameEnded extends MessageFromServer{
    private Map<String, Integer> ranks;
    private Map<String, Integer> resources;
    @Override
    public void activateMessage(Client client) {
        client.setEnded(true);
        client.getGameView().endGame(ranks, resources);
    }
}
