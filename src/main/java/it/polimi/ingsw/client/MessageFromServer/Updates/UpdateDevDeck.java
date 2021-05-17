package it.polimi.ingsw.client.MessageFromServer.Updates;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.MessageFromServer.MessageFromServer;

public class UpdateDevDeck extends MessageFromServer {
    private String player;
    private int position;
    private int id;


    @Override
    public void activateMessage(Client client) {
        client.getGameView().dumpMessage(player + " has bought a new development card  and placed it to deck " + position);
        client.getGameView().getPlayer(player).getDecks().addCard(position, id);
    }
}
