package it.polimi.ingsw.client.MessageFromServer;

public class SelectPlaceCard implements MessageToClient {
    private String type;

    public SelectPlaceCard() {
        type = "SelectPlaceCard";
    }
}
