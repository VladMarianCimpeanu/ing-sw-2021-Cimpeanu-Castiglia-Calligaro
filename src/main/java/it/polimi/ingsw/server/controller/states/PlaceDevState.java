package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.MessageToClient.Error;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.exceptions.WrongLevelException;

import static it.polimi.ingsw.server.controller.states.ErrorMessage.*;

/**
 * This state follows the resources payment needed for the purchase of a development card.
 * It allows the player to choose on which deck the player wants to place the card just drawn
 */
public class PlaceDevState extends TurnState {
    public PlaceDevState(Controller controller) {
        super(controller);
    }

    @Override
    public void placeDevCard(int deck) {
        Controller controller = getController();
        Player player = controller.getCurrentPlayer();
        try {
            player.placeDevelopmentCard(deck);
            controller.setCurrentState(new EndTurnState(controller));
        } catch (WrongLevelException e) {
            controller.sendMessage(new Error(invalidDeck.toString()));
        }
    }

    @Override
    public void completeTurn() {
        try {
            getController().getCurrentPlayer().autoPlace();
        } catch (WrongLevelException e) {
            e.printStackTrace();
        }
        getController().nextTurn();
    }
}
