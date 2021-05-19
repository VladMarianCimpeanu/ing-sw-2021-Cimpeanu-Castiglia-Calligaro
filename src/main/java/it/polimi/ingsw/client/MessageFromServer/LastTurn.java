package it.polimi.ingsw.client.MessageFromServer;

import it.polimi.ingsw.client.Client;

public class LastTurn extends MessageFromServer {
    private String player;
    private String cause;

    @Override
    public void activateMessage(Client client) {
        client.getGameView().dumpMessage("Last turn! " + player + cause);
    }
}
