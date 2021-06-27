package it.polimi.ingsw.client.MessageFromServer.Updates;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.MessageFromServer.MessageFromServer;
import it.polimi.ingsw.client.Color;

/**
 * Message from the server containing color and level of the development card that has just been bought.
 * It also contains the ID of the new available development card that was below the drawn card.
 * If the deck becomes empty, the ID is 0.
 */

public class UpdateDevCardDrawn extends MessageFromServer {
    private Color color;
    private int level;
    private int idNewCard;

    @Override
    public void activateMessage(Client client) {
        client.getGameView().getCards().updateSet(color, level, idNewCard);
    }
}
