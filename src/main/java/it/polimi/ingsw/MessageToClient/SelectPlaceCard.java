package it.polimi.ingsw.MessageToClient;

public class SelectPlaceCard implements MessageToClient{
    private String type;

    public SelectPlaceCard() {
        type = "SelectPlaceCard";
    }
}
