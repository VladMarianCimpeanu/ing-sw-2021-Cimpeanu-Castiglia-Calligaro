package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.MessageToClient.ResourceToPay;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.model.Dashboard;
import it.polimi.ingsw.server.model.ExtraProduction;
import it.polimi.ingsw.server.model.benefit.Resource;
import it.polimi.ingsw.server.model.exceptions.InvalidIDExcpetion;
import it.polimi.ingsw.server.model.exceptions.NotEnoughResourcesException;
import it.polimi.ingsw.server.model.exceptions.ProductionStartedException;
import it.polimi.ingsw.server.model.exceptions.ProductionUsedException;

import java.util.HashMap;
import java.util.Map;


/**
 * In this state the player has activated an extra production and can
 * select the resource wanted in output.
 */
public class ExtraProdOutState extends TurnState {
    private final int extraProductionID;
    private final boolean firstProduction;

    public ExtraProdOutState(Controller controller, int ID, boolean firstProduction) {
        super(controller);
        extraProductionID = ID;
        this.firstProduction = firstProduction;
    }

    @Override
    public void selectOutput(Resource resource) {
        Controller controller = getController();
        Dashboard dashboard = controller.getCurrentPlayer().getDashboard();
        try {
            ExtraProduction extraProduction = dashboard.getExtraProduction(extraProductionID);
            int extraIndex = dashboard.getExtraProductions().indexOf(extraProduction);
            dashboard.selectExtraProduction(extraIndex, resource);
            Resource in = extraProduction.getResourceIn();
            Map<Resource, Integer> input = new HashMap<>();
            input.put(in, 1);
            controller.sendMessage(new ResourceToPay(input));
            controller.setCurrentState(new CardProdState(controller));
        } catch (NotEnoughResourcesException e) {
            resetState(controller, ErrorMessage.notEnoughResources);
        } catch (ProductionStartedException e) {
            resetState(controller, ErrorMessage.productionAlreadyStarted);
        } catch (ProductionUsedException e) {
            resetState(controller, ErrorMessage.productionUsed);
        } catch (InvalidIDExcpetion invalidIDExcpetion) {
            resetState(controller, ErrorMessage.invalidLeaderCardID);
        }

    }

    /**
     * This method should be called when an extra production fails due to "notEnoughResources", "productionUsed" or "InvalidLeaderCard".
     * If the production is the first one used during the current turn, it will set SELECTION STATE as the next turn, in order
     * to avoid skipping any action. If the selected production is not the first activated in this turn, PRODUCTION STATE will be set.
     * @param controller controller of this state
     * @param error error that caused production failure.
     */
    private void resetState(Controller controller, ErrorMessage error) {
        controller.sendError(error.toString());
        if(firstProduction) controller.setCurrentState(new SelectionState(controller));
        else controller.setCurrentState(new ProductionState(controller));
    }

    /**
     * It adds all the resources produced in this turn to the strongbox and makes the current player's dashboard forget
     * all the productions used in the current turn.
     * Then notify the controller to trigger the next turn.
     */
    @Override
    public void completeTurn() {
        if(!firstProduction) getController().getCurrentPlayer().getDashboard().getStrongbox().addProduced();
        getController().getCurrentPlayer().getDashboard().refreshState();
        getController().nextTurn();
    }
}
