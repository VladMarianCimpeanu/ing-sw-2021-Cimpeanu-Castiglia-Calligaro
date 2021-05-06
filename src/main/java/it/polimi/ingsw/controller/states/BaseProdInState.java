package it.polimi.ingsw.controller.states;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.benefit.Resource;

public class BaseProdInState extends TurnState {

    public BaseProdInState(Controller controller) {
        super(controller);
    }

    @Override
    public void selectInput(Resource res1, Resource res2) {

    }
}
