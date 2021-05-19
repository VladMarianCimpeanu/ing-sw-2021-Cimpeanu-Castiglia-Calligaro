package it.polimi.ingsw.client.MessageFromServer.Rejoin;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.MessageFromServer.MessageFromServer;

public class RejoinDecks extends MessageFromServer {
    private String player;
    private int position;
    private int id;

    @Override
    public void activateMessage(Client client) {
        client.getGameView().getPlayer(player).getDecks().addCard(position, id);
    }
}
