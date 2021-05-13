package it.polimi.ingsw.server.MessageFromClient;

import it.polimi.ingsw.server.controller.Controller;

public class CheatFaith extends MessageFromClient{
    private int steps;
    @Override
    public void activate(Controller controller) {
        controller.getCurrentState().activateFaithCheat(steps);
    }
}
