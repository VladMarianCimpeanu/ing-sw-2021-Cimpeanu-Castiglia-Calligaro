package it.polimi.ingsw.controller.states;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Dashboard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.benefit.Resource;
import it.polimi.ingsw.model.exceptions.InvalidResourceException;
import it.polimi.ingsw.model.exceptions.NotEnoughResourcesException;
import it.polimi.ingsw.model.exceptions.RequirementsSatisfiedException;

/**
 * This state follows the player's choice of buying a development card from the development card set
 * It allows the client to specify, once per time, the place where a resource is meant to be removed in order to pay the chosen card
 */
public class BuyDevState extends TurnState {

    public BuyDevState(Controller controller) {
        super(controller);
    }

    @Override
    public void pay(Resource resource, String position) {
        Controller controller = getController();
        if(resource == null){
            controller.sendError("Null Resource found");
            return;
        }
        Player player = controller.getCurrentPlayer();
        Dashboard dashboard = player.getDashboard();
        if(position != null) {
            if (position.equals("WarehouseDepot")) {
                try {
                    dashboard.takeFromDepot(resource);
                } catch (NotEnoughResourcesException | InvalidResourceException | RequirementsSatisfiedException e) {
                    e.printStackTrace();
                }
            } else if (position.equals("Strongbox")) {
                try {
                    dashboard.takeFromStrongbox(resource);
                } catch (NotEnoughResourcesException | InvalidResourceException | RequirementsSatisfiedException e) {
                    e.printStackTrace();
                }
            } else if (position.equals("ExtraSlot")) {
                try {
                    dashboard.takeFromExtraSlot(resource);
                } catch (NotEnoughResourcesException | InvalidResourceException | RequirementsSatisfiedException e) {
                    e.printStackTrace();
                }
            } else {
                controller.sendError("Wrong Position found");
                return;
            }
            if(dashboard.getResourcesToPay().isEmpty()) controller.setCurrentState(new PlaceDevState(controller));
        }else
            controller.sendError("Null Position found");
    }
}
