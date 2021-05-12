package it.polimi.ingsw.client.MessageToServer;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.TurnState;

public class PlaceCard implements MessageToServer {
    private String type;
    private int pos;

    public PlaceCard(int pos) {
        this.type = "PlaceCard";
        this.pos = pos;
    }
}
