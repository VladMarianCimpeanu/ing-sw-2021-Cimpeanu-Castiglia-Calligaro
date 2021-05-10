package it.polimi.ingsw.MessageToClient.Updates;

import it.polimi.ingsw.MessageToClient.MessageToClient;
import it.polimi.ingsw.model.Color;

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
