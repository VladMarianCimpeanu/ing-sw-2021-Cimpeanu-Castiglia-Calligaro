package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.JsonToLeaderCard;
import it.polimi.ingsw.server.MessageToClient.*;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.model.Color;
import it.polimi.ingsw.server.model.Discount;
import it.polimi.ingsw.server.model.benefit.Resource;
import it.polimi.ingsw.server.model.exceptions.*;
import it.polimi.ingsw.server.model.leaderCards.LeaderCard;

import static it.polimi.ingsw.server.controller.states.ErrorMessage.*;
import it.polimi.ingsw.server.model.Dashboard;
import it.polimi.ingsw.server.model.DevelopmentCard;
import it.polimi.ingsw.server.model.market.Marble;

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
            ArrayList<Marble> marbles = getController().getGame().getMarket().getSelectedMarbles();
            ArrayList<String> selected = new ArrayList<>();
            for(Marble m: marbles){
                selected.add(m.toString());
            }
            getController().sendMessage(new SelectedMarbles(selected));
            System.out.println("[" + getController().getCurrentPlayer().getNickName() + "]:" + "Marble from market:");
            System.out.println(selected);

            //if there are no white marbles or the player has no marketStrategies, the marketStrategyState will be skipped.
            if(whiteMarbles == 0 || getController().getCurrentPlayer().getMarketStrategies().isEmpty()){
                getController().getCurrentPlayer().passStrategiesToMarket();
                //if there are no resources to keep, the next state will be the endTurnState.
                if(getController().getCurrentPlayer().isMarketResourcesUnavailable()) getController().setCurrentState(new EndTurnState(getController()));
                else {
                    ArrayList<Resource> resources = getController().getCurrentPlayer().getReceivedFromMarket();
                    getController().sendMessage(new ConvertedMarbles(resources));

                    getController().setCurrentState(new MarketState(getController()));
                }
            }
            else {
                getController().setCurrentState(new MarketStrategyState(getController(), whiteMarbles));
            }

        } catch (OutOfBoundColumnsException | OutOfBoundRowException e) {
            getController().sendError(invalidMarketPosition.toString());
        } catch (InvalidDirectionSelection invalidDirectionSelection) {
            getController().sendError(invalidDirection.toString());
        } catch (InvalidStrategyException e) {
            e.printStackTrace();
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
        Controller controller = getController();
        Dashboard dashboard = controller.getCurrentPlayer().getDashboard();
        DevelopmentCard card = dashboard.getActivableDevCards().get(deckIndex);
        controller.sendMessage(new ResourceToPay(card.getResourceCost()));

        try {
            dashboard.selectCardProduction(deckIndex);
            controller.setCurrentState(new CardProdState(controller));
        } catch (InvalidDeckPositionException e) {
            controller.sendError("invalidDeckIndex");
        } catch (NotEnoughResourcesException e) {
            controller.sendError("notEnoughResources");
        } catch (NoCardException e) {
            controller.sendError("noCard");
        } catch (ProductionStartedException e) {
            controller.sendError("productionStarted");
        } catch (ProductionUsedException e) {
            controller.sendError("productionUsed");
        }
    }

    @Override
    public void activateBase() {
        Controller controller = getController();
        controller.sendMessage(new SelectResourceIn());
        controller.setCurrentState(new BaseProdInState(controller));
    }

    @Override
    public void activateExtra(int id) {
        Controller controller = getController();
        controller.sendMessage(new SelectResourceOut());
        controller.setCurrentState(new ExtraProdOutState(controller, id));
    }
}
