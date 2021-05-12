package it.polimi.ingsw.client.MessageFromServer.Updates;

import it.polimi.ingsw.client.MessageFromServer.MessageFromServer;
import it.polimi.ingsw.server.model.benefit.Resource;

public class UpdateExtraSlot extends MessageFromServer {
    private String player;
    private Resource resource;
    private int id;
    private int quantity;

    @Override
    public void activateMessage() {
        System.out.println(player + "now has " + quantity + " " +  resource + "on his leader card " + id );
    }
}
