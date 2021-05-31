package it.polimi.ingsw.server.controller.stubs;

import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.controller.Controller;

public class EchoStub extends ClientHandler {
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
