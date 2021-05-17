package it.polimi.ingsw.client.MessageFromServer.Updates;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.MessageFromServer.MessageFromServer;

public class UpdateDevDeck extends MessageFromServer {
    private String player;
    private int position;
    private int id;


    @Override
    public void activateMessage(Client client) {
        System.out.println(player + " has bought the  development card " + id + " and placed it to deck " + position);
    }
}
