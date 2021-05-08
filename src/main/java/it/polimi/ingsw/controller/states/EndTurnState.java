package it.polimi.ingsw.controller.states;

import it.polimi.ingsw.JsonToLeaderCard;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.exceptions.NoCardException;
import it.polimi.ingsw.model.exceptions.RequirementException;
import it.polimi.ingsw.model.leaderCards.LeaderCard;

import static it.polimi.ingsw.controller.states.ErrorMessage.*;

public class EndTurnState extends TurnState {

    public EndTurnState(Controller controller) {
        super(controller);
    }

    @Override
    public void end() {
        getController().nextTurn();
        getController().setCurrentState(new SelectionState(getController()));
    }

    @Override
    public void activateLeaderCard(int id) {
        try {
            LeaderCard leaderCard = JsonToLeaderCard.getLeaderCard(id);
            getController().getCurrentPlayer().activateLeaderCard(leaderCard);
        } catch (NoCardException e) {
            getController().sendError(invalidLeaderCardID.toString());
        } catch (RequirementException e) {
            getController().sendError(requirementsNotSatisfied.toString());
        }
    }

    @Override
    public void discardLeaderCard(int id) {
        try {
            LeaderCard leaderCard = JsonToLeaderCard.getLeaderCard(id);
            getController().getCurrentPlayer().discardLeaderCard(leaderCard);
        } catch (NoCardException e) {
            getController().sendError(invalidLeaderCardID.toString());
        }

    }


}
