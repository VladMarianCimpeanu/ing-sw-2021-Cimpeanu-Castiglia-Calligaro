package it.polimi.ingsw.controller.states;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.benefit.Resource;
import it.polimi.ingsw.model.exceptions.*;

import java.util.ArrayList;

import static it.polimi.ingsw.controller.states.ErrorMessage.*;

/**
 * This class represents the state reached by the player during his turn.
 */
public abstract class TurnState {
    private Controller controller;

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
     * This method is used to select the position from which the resource required by the extraProduction must be taken.
     * If this method is performed during the wrong turn, it will send an error message to the controller
     * @param position position of the required resource: values allowed are 'depot', 'strongbox' and 'extraSlot'
     */
    public void selectInputPosition(String position){
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
    
}
