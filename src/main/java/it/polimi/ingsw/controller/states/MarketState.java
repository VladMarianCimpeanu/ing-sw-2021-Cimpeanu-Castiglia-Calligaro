package it.polimi.ingsw.controller.states;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.benefit.Resource;
import it.polimi.ingsw.model.exceptions.*;

import static it.polimi.ingsw.controller.states.ErrorMessage.*;

/**
 * A player is in this state when he must chose where to place the resources taken from the depot.
 */
public class MarketState extends TurnState {

    public MarketState(Controller controller) {
        super(controller);
    }

    @Override
    public void putWarehouse(Resource resource, int shelf) {
        try {
            getController().getCurrentPlayer().putInWarehouseDepot(resource, shelf);
            if(getController().getCurrentPlayer().isMarketResourcesUnavailable()) getController().setCurrentState(new EndTurnState(getController()));
        } catch (InvalidResourceException e) {
            getController().sendError(nullResource.toString());
        } catch (ExistingResourceException e) {
            getController().sendError(existingResource.toString());
        } catch (InvalidShelfPosition e){
            getController().sendError(invalidShelf.toString());
        } catch (NotEnoughSpaceException e) {
            getController().sendError(notEnoughSpace.toString());
        }
    }

    @Override
    public void putExtraSlot(Resource resource) {
        try {
            getController().getCurrentPlayer().putInExtraSlot(resource);
            if(getController().getCurrentPlayer().isMarketResourcesUnavailable()) getController().setCurrentState(new EndTurnState(getController()));
        } catch (NotEnoughSpaceException e) {
            getController().sendError(notEnoughSpace.toString());
        } catch (InvalidResourceException e) {
            getController().sendError(nullResource.toString());
        } catch (MissingExtraSlot missingExtraSlot) {
            getController().sendError(invalidLeaderCardID.toString());
        }
    }

    @Override
    public void discardRes(Resource resource) {
        try {
            getController().getCurrentPlayer().discardResource(resource);
            if(getController().getCurrentPlayer().isMarketResourcesUnavailable()) getController().setCurrentState(new EndTurnState(getController()));
        } catch (InvalidResourceException e) {
            getController().sendError(nullResource.toString());
        }
    }
}
