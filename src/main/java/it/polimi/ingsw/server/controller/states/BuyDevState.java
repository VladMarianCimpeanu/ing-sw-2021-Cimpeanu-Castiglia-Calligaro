package it.polimi.ingsw.server.controller.states;


import it.polimi.ingsw.server.MessageToClient.Error;
import it.polimi.ingsw.server.MessageToClient.ResourceToPay;
import it.polimi.ingsw.server.MessageToClient.SelectPlaceCard;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.benefit.Resource;
import it.polimi.ingsw.server.model.exceptions.GameEndedException;
import it.polimi.ingsw.server.model.exceptions.NotEnoughResourcesException;
import it.polimi.ingsw.server.model.exceptions.WrongLevelException;

import static it.polimi.ingsw.server.controller.states.ErrorMessage.*;

/**
 * This state follows the player's choice of buying a development card from the development card set
 */
public class BuyDevState extends TurnState {

    public BuyDevState(Controller controller) {
        super(controller);
        getController().sendMessage(new ResourceToPay(getController().getCurrentPlayer().getDevelopmentCardCost()));
    }

    /**
     * It allows the player to specify, once per time, the place where a resource is meant to be removed in order to pay the chosen card
     * @param resource specified resource chosen for the payment.
     * @param position specified place from which the resource must be taken. Positions admitted are: 'extraSlot',
     */
    @Override
    public void pay(Resource resource, String position) {
        Controller controller = getController();
        if(resource == null){
            controller.sendMessage(new Error(nullResource));
            return;
        }
        Player player = controller.getCurrentPlayer();
        if(position != null) {
            try {
                if (position.equals("depot"))
                    player.payFromWarehouseDepot(resource);
                else if (position.equals("strongbox"))
                    player.payFromStrongbox(resource);
                else if (position.equals("extra"))
                    player.payFromExtraSlot(resource);
                else {
                    controller.sendMessage(new Error(invalidCommand));
                    return;
                }
                controller.sendMessage(new ResourceToPay(controller.getCurrentPlayer().getDevelopmentCardCost()));
                if (player.getDevelopmentCardCost().isEmpty()) {
                    controller.sendMessage(new SelectPlaceCard());
                    controller.setCurrentState(new PlaceDevState(controller));
                }
            }catch(NotEnoughResourcesException e){
                controller.sendMessage(new Error(notEnoughResources));
            }
        }else
            controller.sendMessage(new Error(nullPosition));

    }

    @Override
    public void completeTurn() {
        try {
            getController().getCurrentPlayer().completePayment();
        } catch (NotEnoughResourcesException e) {
            e.printStackTrace();
        }
        try {
            getController().getCurrentPlayer().autoPlace();
        } catch (WrongLevelException e) {
            e.printStackTrace();
        } catch (GameEndedException gameEndedException) {
            endGame();
            getController().nextTurn();
        }
        getController().nextTurn();
    }
}
