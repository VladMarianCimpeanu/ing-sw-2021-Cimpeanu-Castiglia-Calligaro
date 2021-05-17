package it.polimi.ingsw.client.MessageFromServer.Updates;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.MessageFromServer.MessageFromServer;
import it.polimi.ingsw.server.model.Color;

public class UpdateDevCardDrawn extends MessageFromServer {
    private Color color;
    private int level;
    private int idNewCard;

    @Override
    public void activateMessage(Client client) {
        System.out.println("A " + color + " development card of level " + level + " has just drown.");
        if (idNewCard == 0) System.out.println("There are no more cards.");
        else System.out.println("Now you can draw the card " + idNewCard);
    }
}
