package it.polimi.ingsw.controller.states;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Dashboard;
import it.polimi.ingsw.model.benefit.Resource;
import it.polimi.ingsw.model.exceptions.InvalidResourceException;
import it.polimi.ingsw.model.exceptions.NotEnoughResourcesException;
import it.polimi.ingsw.model.exceptions.RequirementsSatisfiedException;

public class ExtraProdBuyState extends TurnState {
    private Resource resourceIn;

    public ExtraProdBuyState(Controller controller, Resource resourceIn) {
        super(controller);
        this.resourceIn = resourceIn;
    }

    @Override
    public void selectInputPosition(String position) {
        Controller controller = getController();
        Dashboard dashboard = controller.getCurrentPlayer().getDashboard();

        try{
            switch(position){
                case "warehouseDepot":
                    dashboard.takeFromDepot(resourceIn);
                    break;
                case "strongbox":
                    dashboard.takeFromStrongbox(resourceIn);
                    break;
                case "extraSlot":
                    dashboard.takeFromExtraSlot(resourceIn);
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
        controller.setCurrentState(new ActivateProdState(controller));
    }
}
