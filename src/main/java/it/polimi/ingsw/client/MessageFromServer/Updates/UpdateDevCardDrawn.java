package it.polimi.ingsw.client.MessageFromServer.Updates;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.MessageFromServer.MessageFromServer;
import it.polimi.ingsw.client.Color;

public class UpdateDevCardDrawn extends MessageFromServer {
    private Color color;
    private int level;
    private int idNewCard;

    @Override
    public void activateMessage(Client client) {
        client.getGameView().dumpMessage("A " + color + " development card of level " + level + " has just drown.");
        if (idNewCard == 0) client.getGameView().dumpMessage("There are no more cards.");
        else client.getGameView().dumpMessage("Now you can draw the card " + idNewCard);
        client.getGameView().getCards().updateSet(color, level, idNewCard);
    }
}
