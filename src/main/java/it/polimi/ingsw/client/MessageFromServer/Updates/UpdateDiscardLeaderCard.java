package it.polimi.ingsw.client.MessageFromServer.Updates;

import it.polimi.ingsw.client.MessageFromServer.MessageFromServer;

public class UpdateDiscardLeaderCard extends MessageFromServer {
    private String player;
    private int id;

    @Override
    public void activateMessage() {
        System.out.println(player + " discarded the following leader card:" + id );
    }
}
