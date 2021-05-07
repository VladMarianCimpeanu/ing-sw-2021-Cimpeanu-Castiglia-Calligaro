package it.polimi.ingsw.controller.states;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.exceptions.WrongLevelException;

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
        if(deck < 1 || deck > 3) {
            controller.sendError("Illegal Deck position found");
            return;
        }
        Player player = controller.getCurrentPlayer();
        try {
            player.placeDevelopmentCard(deck);
            controller.setCurrentState(new EndTurnState(controller));
        } catch (WrongLevelException e) {
            controller.sendError("The card is not placeable at the specified deck");
        }
    }
}
