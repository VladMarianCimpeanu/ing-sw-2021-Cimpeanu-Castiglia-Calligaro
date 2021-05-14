package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.MessageToClient.SelectResourceOut;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.model.benefit.Resource;

/**
 * In this state the player has activated the base production and can
 * select 2 resources in input to start the production.
 */
public class BaseProdInState extends TurnState {
    private final boolean firstProduction;
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

    /**
     * It adds all the resources produced in this turn to the strongbox and makes the current player's dashboard forget
     * all the productions used in the current turn.
     * Then notify the controller to trigger the next turn.
     */
    @Override
    public void completeTurn() {
        if(!firstProduction) getController().getCurrentPlayer().getDashboard().getStrongbox().addProduced();
        getController().getCurrentPlayer().getDashboard().refreshProductions();
        getController().nextTurn();
    }
}
