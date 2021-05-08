package it.polimi.ingsw.controller.states;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Dashboard;
import it.polimi.ingsw.model.benefit.Resource;
import it.polimi.ingsw.model.exceptions.InvalidResourceException;
import it.polimi.ingsw.model.exceptions.NotEnoughResourcesException;
import it.polimi.ingsw.model.exceptions.RequirementsSatisfiedException;

import java.util.ArrayDeque;
import java.util.Queue;

public class BaseProdPayState extends TurnState {
    private Queue<Resource> resIn;

    public BaseProdPayState(Controller controller, Resource res1In, Resource res2In) {
        super(controller);
        resIn = new ArrayDeque<>();
        resIn.add(res1In);
        resIn.add(res2In);
    }

    @Override
    public void selectInputPosition(String position) {
        Controller controller = getController();
        Dashboard dashboard = controller.getCurrentPlayer().getDashboard();
        Resource resourceIn = resIn.peek();

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
        resIn.poll();
        if(resIn.size() != 0) {
            controller.sendSimple("resToPay", resIn.peek().toString());
        }else {
            controller.setCurrentState(new ActivateProdState(controller));
        }
    }
}
