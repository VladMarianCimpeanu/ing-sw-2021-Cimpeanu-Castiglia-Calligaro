package it.polimi.ingsw.client.MessageToServer;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.TurnState;
import it.polimi.ingsw.client.Resource;

/**
 * Message to server: it contains the desired output resources from a base production.
 */
public class SelResOut implements MessageToServer {
    private String type;
    private Resource resource;

    public SelResOut(Resource resource) {
        this.type = "SelResOut";
        this.resource = resource;
    }
}
