package it.polimi.ingsw.server.controller.stubs;

import it.polimi.ingsw.server.EchoServerClientHandler;
import it.polimi.ingsw.server.controller.Controller;

import java.net.Socket;

public class EchoStub extends EchoServerClientHandler {
    public EchoStub() {
        super(new SocketStub());
    }

    @Override
    public void setController(Controller controller) {
        super.setController(controller);
    }

    @Override
    public Controller getController() {
        return super.getController();
    }
}
