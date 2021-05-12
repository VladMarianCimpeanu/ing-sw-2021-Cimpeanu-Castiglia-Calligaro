package it.polimi.ingsw.client.MessageToServer;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.TurnState;

public class Strategy implements MessageToServer {
    private String type;
    private int id;

    public Strategy(int id) {
        this.type = "Strategy";
        this.id = id;
    }
}
