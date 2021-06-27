package it.polimi.ingsw.client.MessageToServer;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.TurnState;
import it.polimi.ingsw.client.Resource;

/**
 * Message to server: it contains the selected resource for payment and from where to take: strongbox, extra, depot.
 */
public class TakeResPos implements MessageToServer {
    private String type;
    private Resource resource;
    private String position;

    public TakeResPos(Resource resource, String position) {
        this.type = "TakeResPos";
        this.resource = resource;
        this.position = position;
    }
}
