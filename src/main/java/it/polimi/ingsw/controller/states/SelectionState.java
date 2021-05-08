package it.polimi.ingsw.controller.states;

import it.polimi.ingsw.JsonToLeaderCard;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Discount;
import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.model.leaderCards.LeaderCard;

import static it.polimi.ingsw.controller.states.ErrorMessage.*;

import java.util.ArrayList;

public class SelectionState extends TurnState {
    public SelectionState(Controller controller) {
        super(controller);
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

    @Override
    public void goToMarket(String direction, int position) {
        try {
            int whiteMarbles = getController().getCurrentPlayer().goToMarket(direction, position);
            if(whiteMarbles == 0 || getController().getCurrentPlayer().getDiscountList().isEmpty()) getController().setCurrentState(new MarketState(getController()));
            else getController().setCurrentState(new MarketStrategyState(getController()));

        } catch (OutOfBoundColumnsException | OutOfBoundRowException e) {
            getController().sendError(invalidMarketPosition.toString());
        } catch (InvalidDirectionSelection invalidDirectionSelection) {
            getController().sendError(invalidDirection.toString());
        }
    }

    @Override
    public void buyDevCard(Color color, int level, ArrayList<Integer> discountId) {
        ArrayList<Discount> discounts = new ArrayList<>();
        try {
            for(Integer id : discountId) {
                discounts.add(getController().getCurrentPlayer().getDiscount(id));
            }
            getController().getCurrentPlayer().drawDevelopmentCard(color, level, discounts);
            getController().setCurrentState(new BuyDevState(getController()));

        } catch (InvalidIDExcpetion | InvalidDiscountException e) {
            getController().sendError(invalidLeaderCardID.toString());
        } catch (NoCardException e) {
            getController().sendError(noSuchDevelopmentCard.toString());
        } catch (WrongLevelException e) {
            getController().sendError(wrongLevel.toString());
        } catch (NotEnoughResourcesException e) {
            getController().sendError(notEnoughResources.toString());
        }
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
