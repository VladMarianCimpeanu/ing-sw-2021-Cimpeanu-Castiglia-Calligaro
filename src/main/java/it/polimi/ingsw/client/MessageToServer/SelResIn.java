package it.polimi.ingsw.client.MessageToServer;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.TurnState;
import it.polimi.ingsw.client.Resource;

/**
 * Message to server: it contains the input resources for base or extra production.
 */
public class SelResIn implements MessageToServer {
    private String type;
    private Resource res1, res2;

    public SelResIn(Resource res1, Resource res2) {
        this.type = "SelResIn";
        this.res1 = res1;
        this.res2 = res2;
    }
}
