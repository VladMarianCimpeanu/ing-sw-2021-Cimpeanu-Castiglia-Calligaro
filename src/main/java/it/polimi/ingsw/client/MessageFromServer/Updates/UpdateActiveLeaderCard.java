package it.polimi.ingsw.client.MessageFromServer.Updates;

import it.polimi.ingsw.client.MessageFromServer.MessageFromServer;

public class UpdateActiveLeaderCard extends MessageFromServer {
    private String player;
    private int id;

    @Override
    public void activateMessage() {
        System.out.println(player + "activated the following leader card:" + id);
    }
}
