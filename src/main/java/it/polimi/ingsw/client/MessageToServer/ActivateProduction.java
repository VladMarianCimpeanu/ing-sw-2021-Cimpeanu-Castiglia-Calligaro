package it.polimi.ingsw.client.MessageToServer;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.TurnState;

/**
 * Message to servert: it asks the server to activate the selected production.
 */
public class ActivateProduction implements MessageToServer {
    private String type;

    public ActivateProduction() {
        this.type = "ActivateProduction";
    }
}
