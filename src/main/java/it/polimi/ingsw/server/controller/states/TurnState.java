package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.model.Color;
import it.polimi.ingsw.server.model.Strongbox;
import it.polimi.ingsw.server.model.benefit.Resource;
import it.polimi.ingsw.server.model.exceptions.*;

import java.util.ArrayList;

import static it.polimi.ingsw.server.controller.states.ErrorMessage.*;

/**
 * This class represents the state reached by the player during his turn.
 */
public abstract class TurnState {
    private final Controller controller;

    public TurnState(Controller controller) {
        this.controller = controller;
    }

    /**
     * @return the controller that points to the specific game of this turn.
     */
    protected Controller getController() {
        return controller;
    }

    /**
     * It switches resources between the two shelves specified.
     * If this method is performed during the wrong turn, it will send an error message to the controller
     * @param fromShelf shelf from the player wants to move resources
     * @param toShelf shelf to the player wants to move resources
     */
    public void moveWarehouse(int fromShelf, int toShelf) {
        try {
            controller.getCurrentPlayer().getDashboard().getWarehouseDepot().move(fromShelf, toShelf);
        } catch (NotEnoughSpaceException e) {
            controller.sendError(notEnoughSpace.toString());
        } catch (InvalidShelfPosition e) {
            controller.sendError(invalidShelf.toString());
        }
    }

    /**
     * @param shelf specified shelf chosen to place the resources from the extraSlot
     * @param leaderId leaderCard's Id that offers the extra slot from which the resources are taken.
     * @param quantityToMove quantity of resources moved from the extra slot
     */
    public void moveExtraToWarehouse(int shelf, int leaderId, int quantityToMove) {
        try {
            Resource extraSlot = controller.getCurrentPlayer().getDashboard().getWarehouseDepot().getExtraSlot(leaderId).getResource();
            controller.getCurrentPlayer().getDashboard().getWarehouseDepot().moveFromSlotToShelf(extraSlot, quantityToMove, shelf);
        } catch (InvalidIDExcpetion | InvalidResourceException e) {
            controller.sendError(invalidLeaderCardID.toString());
        } catch (NotEnoughSpaceException e) {
            controller.sendError(notEnoughSpace.toString());
        } catch (ExistingResourceException e) {
            controller.sendError(existingResource.toString());
        } catch (InvalidShelfPosition e) {
            controller.sendError(invalidShelf.toString());
        }
    }

    /**
     * @param shelf specified shelf chosen to take resources
     * @param leaderId leaderCard's Id that offers the extra slot where the resources are put.
     * @param quantityToMove quantity of resources moved from the depot
     */
    public void moveWarehouseToExtra(int shelf, int leaderId, int quantityToMove) {
        try {
            controller.getCurrentPlayer().getDashboard().getWarehouseDepot().moveFromShelfToSlot(shelf, quantityToMove);
        } catch (NotEnoughSpaceException e) {
            controller.sendError(notEnoughSpace.toString());
        } catch (MissingExtraSlot missingExtraSlot) {
            controller.sendError(invalidLeaderCardID.toString());
        } catch (InvalidShelfPosition e) {
            controller.sendError(invalidShelf.toString());
        }
    }

    /**
     * activate the specified leaderCard.
     * If this method is performed during the wrong turn, it will send an error message to the controller.
     * @param id leaderCard's ID
     */
    public void activateLeaderCard(int id) {
        controller.sendError(invalidCommand.toString());
    }

    /**
     * discard the specified leaderCard.
     * If this method is performed during the wrong turn, it will send an error message to the controller
     * @param id leaderCard's ID
     */
    public void discardLeaderCard(int id) {
        controller.sendError(invalidCommand.toString());
    }

    /**
     * If this method is performed during the wrong turn, it will send an error message to the controller
     * @param direction row || column
     * @param position row or column position
     */
    public void goToMarket(String direction, int position) {
        controller.sendError(invalidCommand.toString());
    }

    /**
     * select the market strategy offered by the specified leader card.
     * If this method is performed during the wrong turn, it will send an error message to the controller
     * @param id leaderCard's ID
     */
    public void addStrategy(int id) {
        controller.sendError(invalidCommand.toString());
    }

    /**
     * If this method is performed during the wrong turn, it will send an error message to the controller
     * @param resource specified resource to place in depot.
     * @param shelf specified depot's shelf to place the resource.
     */
    public void putWarehouse(Resource resource, int shelf){
        controller.sendError(invalidCommand.toString());
    }

    /**
     * If this method is performed during the wrong turn, it will send an error message to the controller
     * @param resource specified resource to place in an extraSlot.
     */
    public void putExtraSlot(Resource resource){
        controller.sendError(invalidCommand.toString());
    }

    /**
     * If this method is performed during the wrong turn, it will send an error message to the controller
     * @param resource specified resource to discard.
     */
    public void discardRes(Resource resource){
        controller.sendError(invalidCommand.toString());
    }

    /**
     * If this method is performed during the wrong turn, it will send an error message to the controller
     * @param discountId id of the leaderCard to buy.
     */
    public void buyDevCard(Color color, int level, ArrayList<Integer> discountId){
        controller.sendError(invalidCommand.toString());
    }

    /**
     * If this method is performed during the wrong turn, it will send an error message to the controller
     * @param resource specified resource chosen for the payment.
     * @param position specified place from which the resource must be taken. Positions admitted are: 'extraSlot',
     *                 'depot' or 'strongbox'
     */
    public void pay(Resource resource, String position){
        controller.sendError(invalidCommand.toString());
    }

    /**
     * place a dev card in the current player dashboard.
     * If this method is performed during the wrong turn, it will send an error message to the controller
     * @param deck between 1 and 3
     */
    public void placeDevCard(int deck){
        controller.sendError(invalidCommand.toString());
    }

    /**
     * Activate the production of the specified development card.
     * If this method is performed during the wrong turn, it will send an error message to the controller
     * @param deckIndex index of the selected development card (from 1 to 3).
     */
    public void activateDevCard(int deckIndex){
        controller.sendError(invalidCommand.toString());
    }

    /**
     * activate the production power of the selected leader card.
     * If this method is performed during the wrong turn, it will send an error message to the controller
     * @param id ID of the selected leaderCard.
     */
    public void activateExtra(int id){
        controller.sendError(invalidCommand.toString());
    }

    /**
     * Select the desired output of the selected production.
     * If this method is performed during the wrong turn, it will send an error message to the controller
     * @param resource specified resource to take after the production.
     */
    public void selectOutput(Resource resource){
        controller.sendError(invalidCommand.toString());
    }

    /**
     * If this method is performed during the wrong turn, it will send an error message to the controller
     */
    public void activateBase(){
        controller.sendError(invalidCommand.toString());
    }

    /**
     * If this method is performed during the wrong turn, it will send an error message to the controller
     * @param res1 first resource to take as input for the production
     * @param res2 second resource to take as input for the production
     */
    public void selectInput(Resource res1, Resource res2){
        controller.sendError(invalidCommand.toString());
    }

    /**
     * Activate the selected production.
     * If this method is performed during the wrong turn, it will send an error message to the controller
     */
    public void activateProduction(){
        controller.sendError(invalidCommand.toString());
    }

    /**
     * End the current player turn. It notify the controller to change the current player with the next player.
     * If this method is performed during the wrong turn, it will send an error message to the controller
     */
    public void end(){
        controller.sendError(invalidCommand.toString());
    }

    /**
     * At the beginning of the game, this method is used to choose which leader cards the player wants to keep.
     * @param nickname nickname of the player that did the choice.
     * @param id1 ID of a a specific leader card to keep.
     * @param id2 ID of a a specific leader card to keep.
     */
    public void keepLeaderCards(String nickname, int id1, int id2) {
        controller.sendError(invalidCommand.toString());
    };

    /**
     * At the beginning of the game, this method is used to choose which resources the player wants to put in depot.
     * @param nickname nickname of the player that did the choice.
     * @param res1 specific resource to put in depot.
     * @param res2 additional resource to put in depot: if the player is not allowed to choose a second resource, set it to null.
     * @param shelf1 specific shelf to put the first resource.
     * @param shelf2 specific shelf to put the additional resource: if the player is not allowed to choose a second resource, set it to null.
     */
    public void selectResources(String nickname, Resource res1, Resource res2, int shelf1, int shelf2) {
        controller.sendError(invalidCommand.toString());
    };

    /**
     * cheat used during beta testing.
     */
    public void activateResCheat() {
        Strongbox strongbox = controller.getCurrentPlayer().getDashboard().getStrongbox();
        for(Resource res : Resource.values()) {
            try {
                strongbox.addResource(res,50);
            } catch (NegativeQuantityException e) {
                e.printStackTrace();
            }
        }
        strongbox.addProduced();
    }

    /**
     * cheat used during beta testing: move the player by the specified steps.
     */
    public void activateFaithCheat(int steps) {
        getController().getCurrentPlayer().addFaithPoint(steps);
    }
}
