package it.polimi.ingsw.client.MessageFromServer;

import it.polimi.ingsw.client.Client;

public class JoinPlayer extends MessageFromServer {
    private String player;

    @Override
    public void activateMessage(Client client) {
        client.getGameView().dumpMessage(player + "joined the game");
    }
}
