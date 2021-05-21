package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.model.Dashboard;
import it.polimi.ingsw.server.model.benefit.Resource;
import it.polimi.ingsw.server.model.exceptions.InvalidResourceException;
import it.polimi.ingsw.server.model.exceptions.NoProductionAvailableException;
import it.polimi.ingsw.server.model.exceptions.NotEnoughResourcesException;
import it.polimi.ingsw.server.model.exceptions.RequirementsSatisfiedException;

/**
 * In this state the player has activated a production and can
 * pay one by one all the resources required to produce.
 */
public class CardProdState extends TurnState {

    public CardProdState(Controller controller) {
        super(controller);
    }

    @Override
    public void pay(Resource resource, String position) {
        Controller controller = getController();
        Dashboard dashboard = controller.getCurrentPlayer().getDashboard();
        try{
            switch(position){
                case "depot":    //warehouseDepot
                    dashboard.takeFromDepot(resource);
                    break;
                case "strongbox":
                    dashboard.takeFromStrongbox(resource);
                    break;
                case "extraSlot":
                    dashboard.takeFromExtraSlot(resource);
                    break;
                default:
                    controller.sendError(ErrorMessage.invalidStorage);
            }
        } catch (NotEnoughResourcesException e) {
            controller.sendError(ErrorMessage.notEnoughResources);
        } catch (RequirementsSatisfiedException e) {
            controller.sendError(ErrorMessage.requirementsNotSatisfied);
        } catch (InvalidResourceException e) {
            controller.sendError(ErrorMessage.invalidResource);
        }
        if(dashboard.getResourcesToPay().size() == 0){
            controller.setCurrentState(new ActivateProdState(controller));
        }
    }

    /**
     * it ends the production payment not completed by the current player and notify the controller to set the next player to play.
     */
    @Override
    public void completeTurn() {
        Dashboard playersDashboard = getController().getCurrentPlayer().getDashboard();
        try {
            playersDashboard.automatizePayment();
            playersDashboard.activateProduction();
            playersDashboard.getStrongbox().addProduced();
            playersDashboard.refreshState();
            getController().nextTurn();
        } catch (NoProductionAvailableException e) {
            e.printStackTrace();
        }
    }
}
