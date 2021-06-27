package it.polimi.ingsw.server.MessageToClient;

/**
 * Message to client: it notifies the player that the development card purchase process is ended.
 */
public class SelectPlaceCard implements MessageToClient{
    private String type;

    public SelectPlaceCard() {
        type = "SelectPlaceCard";
    }
}
