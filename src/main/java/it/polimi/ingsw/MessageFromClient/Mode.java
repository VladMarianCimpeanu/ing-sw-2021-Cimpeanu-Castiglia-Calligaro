package it.polimi.ingsw.MessageFromClient;

import it.polimi.ingsw.controller.Controller;

public class Mode extends MessageFromClient {
    private int mode;
    @Override
    public void activate(Controller controller) {

    }

    public int getMode() {
        return mode;
    }
}
