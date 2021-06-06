package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.JsonToLeaderCard;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.model.exceptions.GameEndedException;
import it.polimi.ingsw.server.model.exceptions.NoCardException;
import it.polimi.ingsw.server.model.exceptions.RequirementException;
import it.polimi.ingsw.server.model.leaderCards.LeaderCard;

import static it.polimi.ingsw.server.controller.states.ErrorMessage.*;

/**
 * A player is in this state when is going to end his turn.
 * Here he can only activate or discard leader cards or end the turn.
 */
public class EndTurnState extends TurnState {

    public EndTurnState(Controller controller) {
        super(controller);
    }

    /**
     * Finalize the current turn, passing it to the next player
     */
    @Override
    public void end() {
        getController().nextTurn();
    }

    @Override
    public void completeTurn() {
        end();
    }

    /**
     * Activate a leaderCard
     * @param id leaderCard's ID
     */
    @Override
    public void activateLeaderCard(int id) {
        try {
            LeaderCard leaderCard = JsonToLeaderCard.getLeaderCard(id);
            getController().getCurrentPlayer().activateLeaderCard(leaderCard);
        } catch (NoCardException e) {
            getController().sendError(invalidLeaderCardID);
        } catch (RequirementException e) {
            getController().sendError(requirementsNotSatisfied);
        }
    }

    /**
     * Discard a leaderCard
     * @param id leaderCard's ID
     */
    @Override
    public void discardLeaderCard(int id) {
        try {
            LeaderCard leaderCard = JsonToLeaderCard.getLeaderCard(id);
            getController().getCurrentPlayer().discardLeaderCard(leaderCard);
        } catch (NoCardException e) {
            getController().sendError(invalidLeaderCardID);
        } catch (GameEndedException gameEndedException) {
            endGame();
        }

    }


}
