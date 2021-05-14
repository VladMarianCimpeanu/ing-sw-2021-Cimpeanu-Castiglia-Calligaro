package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.MessageToClient.SelectResourceOut;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.model.benefit.Resource;

/**
 * In this state the player has activated the base production and can
 * select 2 resources in input to start the production.
 */
public class BaseProdInState extends TurnState {
    private boolean firstProduction;
    public BaseProdInState(Controller controller, boolean firstProduction) {
        super(controller);
        this.firstProduction = firstProduction;
    }

    @Override
    public void selectInput(Resource res1, Resource res2) {
        Controller controller = getController();
        controller.sendMessage(new SelectResourceOut());
        controller.setCurrentState(new BaseProdOutState(controller, res1, res2, firstProduction));
    }

    @Override
    public void completeTurn() {

    }
}
