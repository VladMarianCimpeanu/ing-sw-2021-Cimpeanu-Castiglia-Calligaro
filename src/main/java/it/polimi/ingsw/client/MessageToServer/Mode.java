package it.polimi.ingsw.client.MessageToServer;

import it.polimi.ingsw.server.controller.Controller;

public class Mode extends MessageFromClient {
    private int mode;
    @Override
    public void activate(Controller controller) {

    }

    public int getMode() {
        return mode;
    }
}
