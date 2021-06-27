package it.polimi.ingsw.client.MessageToServer;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.TurnState;
import it.polimi.ingsw.client.Resource;

/**
 * Message to server: it contains the resources to place in the stocks. It specifies the location with the keywords:
 * extra, depot or strongbox. If the keyword is depot, it contains also an Integer representing the shelf.
 */
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
