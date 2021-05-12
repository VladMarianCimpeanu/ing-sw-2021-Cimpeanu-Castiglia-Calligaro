package it.polimi.ingsw.client.MessageToServer;

import it.polimi.ingsw.server.controller.Controller;

public abstract class MessageFromClient {
    public abstract void activate(Controller controller);

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
