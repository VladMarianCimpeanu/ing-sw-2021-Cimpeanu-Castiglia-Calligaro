package it.polimi.ingsw.client.MessageToServer;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.TurnState;

/**
 * Message to server: it contains the id of the leader card selected in order to use its extra production power.
 */
public class ActivateExtraProduction implements MessageToServer {
    private String type;
    private int id;

    public ActivateExtraProduction(int id) {
        this.type = "ActivateExtraProduction";
        this.id = id;
    }
}
