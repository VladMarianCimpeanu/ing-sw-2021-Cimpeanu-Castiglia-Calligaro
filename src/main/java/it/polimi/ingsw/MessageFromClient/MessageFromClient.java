package it.polimi.ingsw.MessageFromClient;

import it.polimi.ingsw.controller.Controller;

public abstract class MessageFromClient {
    public abstract void activate(Controller controller);

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
