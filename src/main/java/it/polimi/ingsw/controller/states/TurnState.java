package it.polimi.ingsw.controller.states;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.benefit.Resource;

import java.util.ArrayList;

public abstract class TurnState {
    private Controller controller;

    public TurnState(Controller controller) {
        this.controller = controller;
    }

    protected Controller getController() {
        return controller;
    }

    public void moveWarehouse(int fromShelf, int toShelf) {

    }

    public void moveExtraToWarehouse(int shelf, int leaderId, int quantityToMove) {

    }

    public void moveWarehouseToExtra(int shelf, int leaderId, int quantityToMove) {

    }

    public void activateLeaderCard(int id) {

    }

    public void discardLeaderCard(int id) {

    }

    /**
     * @param direction row || column
     * @param position row or column position
     */
    public void goToMarket(String direction, int position) {

    }

    public void addStrategy(int id) {

    }

    public void putWarehouse(Resource resource, int shelf){

    }

    public void putExtraSlot(Resource resource){

    }

    public void discardRes(Resource resource){

    }

    /**
     * @param discountId id of the leaderCard
     */
    public void buyDevCard(Color color, int level, ArrayList<Integer> discountId){

    }

    public void pay(Resource resource, String position){

    }

    /**
     * place a dev card
     * @param deck between 1 and 3
     */
    public void placeDevCard(int deck){

    }

    public void activateDevCard(int deckIndex){

    }

    public void activateExtra(int id){

    }

    public void selectOutput(Resource resource){

    }

    public void selectInputPosition(String position){

    }

    public void activateBase(int id){

    }

    public void selectInput(Resource res1, Resource res2){

    }

    public void activateProduction(){

    }

    public void end(){
        
    }
}
