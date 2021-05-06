package it.polimi.ingsw.controller.states;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.benefit.Resource;

public class CardProdState extends TurnState {

    public CardProdState(Controller controller) {
        super(controller);
    }

    @Override
    public void pay(Resource resource, String position) {

    }
}
