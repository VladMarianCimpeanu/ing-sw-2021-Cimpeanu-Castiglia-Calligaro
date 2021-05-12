package it.polimi.ingsw.client.MessageToServer;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.TurnState;

public class ActivateCardProduction implements MessageToServer {
    private String type;
    private int index;

    public ActivateCardProduction(int index) {
        this.type = "ActivateCardProduction";
        this.index = index;
    }
}
