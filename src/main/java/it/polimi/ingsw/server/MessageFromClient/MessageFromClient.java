package it.polimi.ingsw.server.MessageFromClient;

import it.polimi.ingsw.server.controller.Controller;

/**
 * Message object received from the client.
 */
public abstract class MessageFromClient {
    public abstract void activate(Controller controller);

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public boolean isPing(){
        return false;
    }
}
