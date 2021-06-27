package it.polimi.ingsw.server.MessageFromClient;

import it.polimi.ingsw.server.controller.Controller;

/**
 * Message from client: it contains the size of the game to create.
 */
public class Mode extends MessageFromClient {
    private int mode;
    @Override
    public void activate(Controller controller) {

    }

    public int getMode() {
        return mode;
    }
}
