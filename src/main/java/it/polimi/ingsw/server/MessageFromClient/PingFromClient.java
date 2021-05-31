package it.polimi.ingsw.server.MessageFromClient;

import it.polimi.ingsw.server.controller.Controller;

public class PingFromClient extends MessageFromClient{
    @Override
    public void activate(Controller controller) {
        System.out.println("Ping");
    }
    @Override
    public boolean isPing(){
        return true;
    }
}
