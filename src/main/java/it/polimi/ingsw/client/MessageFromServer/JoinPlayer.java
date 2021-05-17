package it.polimi.ingsw.client.MessageFromServer;

import it.polimi.ingsw.client.Client;

public class JoinPlayer extends MessageFromServer {
    private String player;

    @Override
    public void activateMessage(Client client) {
        System.out.println(player + "joined the game");
    }
}
