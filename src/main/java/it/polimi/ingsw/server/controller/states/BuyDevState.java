package it.polimi.ingsw.server.controller.states;


import it.polimi.ingsw.server.MessageToClient.Error;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.model.Dashboard;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.benefit.Resource;
import it.polimi.ingsw.server.model.exceptions.InvalidResourceException;
import it.polimi.ingsw.server.model.exceptions.NotEnoughResourcesException;
import it.polimi.ingsw.server.model.exceptions.RequirementsSatisfiedException;

import static it.polimi.ingsw.server.controller.states.ErrorMessage.*;

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
            controller.sendMessage(new Error(nullResource.toString()));
            return;
        }
        Player player = controller.getCurrentPlayer();
        Dashboard dashboard = player.getDashboard();
        if(position != null) {
            try {
                if (position.equals("WarehouseDepot"))
                        dashboard.takeFromDepot(resource);
                else if (position.equals("Strongbox"))
                    dashboard.takeFromStrongbox(resource);
                else if (position.equals("ExtraSlot"))
                        dashboard.takeFromExtraSlot(resource);
                else {
                    controller.sendMessage(new Error(invalidCommand.toString()));
                    return;
                }
                if (dashboard.getResourcesToPay().isEmpty()) controller.setCurrentState(new PlaceDevState(controller));
            }catch(NotEnoughResourcesException e){
                controller.sendMessage(new Error(notEnoughResources.toString()));
            }catch(InvalidResourceException e){
                controller.sendMessage(new Error(invalidResource.toString()));
            }catch(RequirementsSatisfiedException e){
                controller.sendMessage(new Error(requirementsNotSatisfied.toString()));
            }
        }else
            controller.sendMessage(new Error(nullPosition.toString()));
    }
}
