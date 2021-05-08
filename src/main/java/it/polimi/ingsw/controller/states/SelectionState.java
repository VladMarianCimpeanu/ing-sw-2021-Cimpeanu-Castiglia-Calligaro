package it.polimi.ingsw.controller.states;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Color;

import java.util.ArrayList;

public class SelectionState extends TurnState {
    public SelectionState(Controller controller) {
        super(controller);
    }

    @Override
    public void activateLeaderCard(int id) {

    }

    @Override
    public void discardLeaderCard(int id) {

    }

    @Override
    public void goToMarket(String direction, int position) {

    }

    @Override
    public void buyDevCard(Color color, int level, ArrayList<Integer> discountId) {

    }

    @Override
    public void activateDevCard(int deckIndex) {

    }

    @Override
    public void activateBase() {

    }

    @Override
    public void activateExtra(int id) {

    }
}
