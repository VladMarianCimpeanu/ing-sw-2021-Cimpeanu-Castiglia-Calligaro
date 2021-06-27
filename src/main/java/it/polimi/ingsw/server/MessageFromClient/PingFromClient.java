package it.polimi.ingsw.server.MessageFromClient;

import it.polimi.ingsw.server.controller.Controller;

/**
 * Ping from client used to check if the connection is still alive with the server.
 */
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
