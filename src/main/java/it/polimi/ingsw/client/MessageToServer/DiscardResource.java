package it.polimi.ingsw.client.MessageToServer;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.TurnState;
import it.polimi.ingsw.client.Resource;

public class DiscardResource implements MessageToServer {
    private String type;
    private Resource resource;

    public DiscardResource(Resource resource) {
        this.type = "DiscardResource";
        this.resource = resource;
    }
}
