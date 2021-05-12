package it.polimi.ingsw.controller.states;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Dashboard;
import it.polimi.ingsw.model.benefit.Resource;
import it.polimi.ingsw.model.exceptions.InvalidResourceException;
import it.polimi.ingsw.model.exceptions.NotEnoughResourcesException;
import it.polimi.ingsw.model.exceptions.RequirementsSatisfiedException;

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
                case "warehouseDepot":
                    dashboard.takeFromDepot(resource);
                    break;
                case "strongbox":
                    dashboard.takeFromStrongbox(resource);
                    break;
                case "extraSlot":
                    dashboard.takeFromExtraSlot(resource);
                    break;
                default:
                    controller.sendError("wrongPosition");
            }
        } catch (NotEnoughResourcesException e) {
            controller.sendError("notEnoughResources");
        } catch (RequirementsSatisfiedException e) {
            controller.sendError("requirementsSatisfied");
        } catch (InvalidResourceException e) {
            controller.sendError("invalidResource");
        }
        if(dashboard.getResourcesToPay().size() == 0){
            controller.setCurrentState(new ActivateProdState(controller));
        }
    }
}
