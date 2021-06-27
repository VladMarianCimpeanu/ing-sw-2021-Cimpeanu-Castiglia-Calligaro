package it.polimi.ingsw.server.MessageToClient.Updates;

import it.polimi.ingsw.server.MessageToClient.MessageToClient;
import it.polimi.ingsw.server.model.Color;


/**
 * Message to the client containing color and level of the development card that has just been bought.
 * It also contains the ID of the new available development card that was below the drawn card.
 * If the deck becomes empty, the ID is 0.
 */
public class UpdateDevCardDrawn implements MessageToClient {
    private String type;
    private Color color;
    private int level;
    private int idNewCard;

    public UpdateDevCardDrawn(Color color, int level, int idNewCard) {
        this.type = "UpdateDevCardDrawn";
        this.color = color;
        this.level = level;
        this.idNewCard = idNewCard;
    }
}
