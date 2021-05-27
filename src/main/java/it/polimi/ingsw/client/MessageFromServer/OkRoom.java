package it.polimi.ingsw.client.MessageFromServer;

import it.polimi.ingsw.client.Client;

import java.util.ArrayList;

public class OkRoom extends MessageFromServer {
    private String message;
    private int size;
    private ArrayList<String> players;

    @Override
    public void activateMessage(Client client) {
        client.getGameView().notifyJoin(message, size, players);
    }
}
