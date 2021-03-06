package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.model.Dashboard;
import it.polimi.ingsw.server.model.ExtraSlot;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.WarehouseDepot;
import it.polimi.ingsw.server.model.benefit.Resource;
import it.polimi.ingsw.server.model.exceptions.*;

import java.util.ArrayList;

import static it.polimi.ingsw.server.controller.states.ErrorMessage.*;

/**
 * A player is in this state when he must chose where to place the resources taken from the depot.
 */
public class MarketState extends TurnState {

    public MarketState(Controller controller) {
        super(controller);
    }

    /**
     * Whether the destination of the selected resource is a WarehouseDepot's shelf
     * @param resource specified resource to place in depot.
     * @param shelf specified depot's shelf to place the resource.
     */
    @Override
    public void putWarehouse(Resource resource, int shelf) {
        try {
            getController().getCurrentPlayer().putInWarehouseDepot(resource, shelf);
            if(getController().getCurrentPlayer().isMarketResourcesUnavailable()) getController().setCurrentState(new EndTurnState(getController()));
        } catch (InvalidResourceException e) {
            getController().sendError(nullResource);
        } catch (ExistingResourceException e) {
            getController().sendError(existingResource);
        } catch (InvalidShelfPosition e){
            getController().sendError(invalidShelf);
        } catch (NotEnoughSpaceException e) {
            getController().sendError(notEnoughSpace);
        } catch (GameEndedException gameEndedException) {
            endGame();
            if(getController().getCurrentPlayer().isMarketResourcesUnavailable()) getController().setCurrentState(new EndTurnState(getController()));
        }
    }

    /**
     * Whether the destination of the selected resource is an ExtraSlot
     * @param resource specified resource to place in an extraSlot.
     */
    @Override
    public void putExtraSlot(Resource resource) {
        try {
            getController().getCurrentPlayer().putInExtraSlot(resource);
            if(getController().getCurrentPlayer().isMarketResourcesUnavailable()) getController().setCurrentState(new EndTurnState(getController()));
        } catch (NotEnoughSpaceException e) {
            getController().sendError(notEnoughSpace);
        } catch (InvalidResourceException e) {
            getController().sendError(nullResource);
        } catch (MissingExtraSlot missingExtraSlot) {
            getController().sendError(invalidLeaderCardID);
        } catch (GameEndedException gameEndedException) {
            endGame();
            if(getController().getCurrentPlayer().isMarketResourcesUnavailable()) getController().setCurrentState(new EndTurnState(getController()));
        }
    }

    /**
     * Discard the selected resource
     * @param resource specified resource to discard.
     */
    @Override
    public void discardRes(Resource resource) {
        try {
            getController().getCurrentPlayer().discardResource(resource);
            if(getController().getCurrentPlayer().isMarketResourcesUnavailable()) getController().setCurrentState(new EndTurnState(getController()));
        } catch (InvalidResourceException e) {
            getController().sendError(nullResource);
        } catch (GameEndedException gameEndedException) {
            endGame();
            if(getController().getCurrentPlayer().isMarketResourcesUnavailable()) getController().setCurrentState(new EndTurnState(getController()));
        }
    }


    @Override
    public void completeTurn() {
        Player player = getController().getCurrentPlayer();
        Dashboard dashboard = player.getDashboard();
        WarehouseDepot depot = dashboard.getWarehouseDepot();
        if(player.isMarketResourcesUnavailable()) getController().nextTurn();

        //First look at the ExtraSlots available
        ArrayList<Resource> resources = player.getReceivedFromMarket();
        for (Resource resource: resources) {
            if(depot.getExtraSlotList().contains(resource)){
                try {
                    player.putInExtraSlot(resource);
                } catch (NotEnoughSpaceException | InvalidResourceException | MissingExtraSlot e) {
                    e.printStackTrace();
                } catch (GameEndedException gameEndedException) {
                    endGame();
                    getController().nextTurn();
                }
            }
        }

        //Then look at the shelves
        resources = player.getReceivedFromMarket();
        for(Resource resource: resources){
            if(depot.getResourceQuantity(resource) != 0) {
                try {
                    player.putInWarehouseDepot(resource, depot.getResourceShelf(resource));
                } catch (InvalidResourceException | ExistingResourceException | InvalidShelfPosition e) {
                    e.printStackTrace();
                } catch (NotEnoughSpaceException e) {
                    try {
                        player.discardResource(resource);
                    } catch (InvalidResourceException invalidResourceException) {
                        invalidResourceException.printStackTrace();
                    } catch (GameEndedException gameEndedException) {
                        endGame();
                        getController().nextTurn();
                    }
                } catch (GameEndedException gameEndedException) {
                    endGame();
                    getController().nextTurn();
                }
            }else{
                try {
                    int shelf = depot.getFreeShelf(3);
                    try {

                        player.putInWarehouseDepot(resource, shelf);
                    } catch (InvalidResourceException | ExistingResourceException | NotEnoughSpaceException e) {
                        e.printStackTrace();
                    } catch (GameEndedException gameEndedException) {
                        endGame();
                        getController().nextTurn();
                    }
                } catch (InvalidShelfPosition invalidShelfPosition) {
                    try {
                        player.discardResource(resource);
                    } catch (InvalidResourceException invalidResourceException) {
                        invalidResourceException.printStackTrace();
                    } catch (GameEndedException gameEndedException) {
                        endGame();
                        getController().nextTurn();
                    }
                }
            }
        }
        getController().nextTurn();
    }
}
