package it.polimi.ingsw.controller.states;

import it.polimi.ingsw.JsonToLeaderCard;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Dashboard;
import it.polimi.ingsw.model.DevelopmentCard;
import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.model.leaderCards.LeaderCard;

public class ProductionState extends TurnState {

    public ProductionState(Controller controller) {
        super(controller);
    }

    @Override
    public void activateDevCard(int deckIndex) {
        Controller controller = getController();
        Dashboard dashboard = controller.getCurrentPlayer().getDashboard();
        DevelopmentCard card = dashboard.getActivableDevCards().get(deckIndex);
        controller.sendSimple("resourceCost", card.getResourceCost().toString());

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
        controller.sendSimple("select","resIn");
        controller.setCurrentState(new BaseProdInState(controller));
    }

    @Override
    public void activateExtra(int id) {
        Controller controller = getController();
        controller.sendSimple("select","resOut");
        controller.setCurrentState(new ExtraProdOutState(controller, id));
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
            getController().setCurrentState(new EndTurnState(getController()));
        } catch (NoCardException e) {
            getController().sendError("invalidLeaderCardID");
        } catch (RequirementException e) {
            getController().sendError("Requirements not satisfied");
        }
    }

    @Override
    public void discardLeaderCard(int id) {
        try {
            LeaderCard leaderCard = JsonToLeaderCard.getLeaderCard(id);
            getController().getCurrentPlayer().discardLeaderCard(leaderCard);
            getController().setCurrentState(new EndTurnState(getController()));
        } catch (NoCardException e) {
            getController().sendError("invalidLeaderCardID");
        }

    }
}