package it.polimi.ingsw.client.MessageToServer;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.TurnState;

public class ActivateProduction implements MessageToServer {
    private String type;

    public ActivateProduction() {
        this.type = "ActivateProduction";
    }
}
