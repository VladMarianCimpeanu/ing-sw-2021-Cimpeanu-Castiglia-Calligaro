package it.polimi.ingsw.client.MessageToServer;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.TurnState;

/**
 * Message to server: it notifies the server that the client has just select to use a base production power.
 */
public class ActivateBaseProduction implements MessageToServer {
    private String type;

    public ActivateBaseProduction() {
        this.type = "ActivateBaseProduction";
    }
}
