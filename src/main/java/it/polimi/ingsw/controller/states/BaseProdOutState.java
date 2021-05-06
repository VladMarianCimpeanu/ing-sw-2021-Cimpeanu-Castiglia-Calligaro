package it.polimi.ingsw.controller.states;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.benefit.Resource;

public class BaseProdOutState extends TurnState {

    public BaseProdOutState(Controller controller) {
        super(controller);
    }

    @Override
    public void selectOutput(Resource resource) {

    }
}
