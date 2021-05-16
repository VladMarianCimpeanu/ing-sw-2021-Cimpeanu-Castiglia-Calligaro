package it.polimi.ingsw.server.controller.stubs;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.model.Identity;

import java.util.ArrayList;

public class ControllerStub extends Controller {


    public ControllerStub(ArrayList<Identity> users) {
        super(users);

    }
}
