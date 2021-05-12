package it.polimi.ingsw.server.MessageToClient;

public class SelectPlaceCard implements MessageToClient{
    private String type;

    public SelectPlaceCard() {
        type = "SelectPlaceCard";
    }
}
