package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.MessageToClient.ResourceToPay;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.model.Dashboard;
import it.polimi.ingsw.server.model.benefit.Resource;
import it.polimi.ingsw.server.model.exceptions.*;

/**
 * In this state the player has activated a production and can
 * pay one by one all the resources required to produce.
 */
public class CardProdState extends TurnState {

    public CardProdState(Controller controller) {
        super(controller);
    }

    /**
     * It allows the player to specify, once per time, the place where a resource is meant to be removed in order to
     * activate the production
     * @param resource specified resource chosen for the payment.
     * @param position specified place from which the resource must be taken. Positions admitted are: 'extraSlot',
     */
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
                case "extra":
                    dashboard.takeFromExtraSlot(resource);
                    break;
                default:
                    controller.sendError(ErrorMessage.invalidStorage);
            }
            controller.sendMessage(new ResourceToPay(controller.getCurrentPlayer().getDashboard().getResourcesToPayMap()));
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
            int faithPoints = playersDashboard.activateProduction();
            getController().getCurrentPlayer().addFaithPoint(faithPoints);
            playersDashboard.getStrongbox().addProduced();
            playersDashboard.refreshState();
            getController().nextTurn();
        } catch (NoProductionAvailableException e) {
            e.printStackTrace();
        } catch (GameEndedException gameEndedException) {
            endGame();
            playersDashboard.getStrongbox().addProduced();
            playersDashboard.refreshState();
            getController().nextTurn();
        }
    }
}
