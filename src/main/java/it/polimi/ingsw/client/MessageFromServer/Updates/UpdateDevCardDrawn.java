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
        client.getGameView().getCards().updateSet(color, level, idNewCard);
    }
}
