package it.polimi.ingsw.client.MessageToServer;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.TurnState;
import it.polimi.ingsw.server.model.benefit.Resource;

public class PutResPos implements MessageToServer {
    private String type;
    private Resource res;
    private String pos;
    private int shelf;

    public PutResPos(Resource res, String pos, int shelf) {
        this.type = "PutResPos";
        this.res = res;
        this.pos = pos;
        this.shelf = shelf;
    }
}
