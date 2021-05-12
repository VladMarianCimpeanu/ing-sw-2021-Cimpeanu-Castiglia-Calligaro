package it.polimi.ingsw.client.MessageToServer;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.TurnState;

public class Market implements MessageToServer {
    private String type;
    private String direction;
    private int position;

    public Market(String direction, int position) {
        this.type = "Market";
        this.direction = direction;
        this.position = position;
    }
}
