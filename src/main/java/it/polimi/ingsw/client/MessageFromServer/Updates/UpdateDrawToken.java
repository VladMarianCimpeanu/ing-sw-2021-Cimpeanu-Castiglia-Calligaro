package it.polimi.ingsw.client.MessageFromServer.Updates;


import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.MessageFromServer.MessageFromServer;

public class UpdateDrawToken extends MessageFromServer {
    private int id;
    @Override
    public void activateMessage(Client client) {
        client.getGameView().getActionTokenView().show(id);
    }
}
