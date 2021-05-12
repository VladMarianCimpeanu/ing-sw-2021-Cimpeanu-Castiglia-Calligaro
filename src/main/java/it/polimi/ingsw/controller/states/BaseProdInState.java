package it.polimi.ingsw.controller.states;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.benefit.Resource;

/**
 * In this state the player has activated the base production and can
 * select 2 resources in input to start the production.
 */
public class BaseProdInState extends TurnState {

    public BaseProdInState(Controller controller) {
        super(controller);
    }

    @Override
    public void selectInput(Resource res1, Resource res2) {
        Controller controller = getController();
        controller.sendSimple("select","resOut");
        controller.setCurrentState(new BaseProdOutState(controller, res1, res2));
    }
}
