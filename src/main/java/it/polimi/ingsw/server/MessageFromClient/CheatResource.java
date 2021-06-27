package it.polimi.ingsw.server.MessageFromClient;

import it.polimi.ingsw.server.controller.Controller;



public class CheatResource extends MessageFromClient{
    @Override
    public void activate(Controller controller) {
        controller.getCurrentState().activateResCheat();
    }
}
