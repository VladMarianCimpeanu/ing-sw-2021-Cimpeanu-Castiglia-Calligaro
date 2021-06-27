package it.polimi.ingsw.client.MessageFromServer;

import it.polimi.ingsw.client.Client;

import java.util.ArrayList;

/**
 * Message from server: it contains the size of the waiting room and a list of nicknames of players that are waiting.
 * It is sent when the client join a waiting room.
 */
public class OkRoom extends MessageFromServer {
    private String message;
    private int size;
    private ArrayList<String> players;

    @Override
    public void activateMessage(Client client) {
        client.getGameView().notifyJoin(message, size, players);
    }
}
