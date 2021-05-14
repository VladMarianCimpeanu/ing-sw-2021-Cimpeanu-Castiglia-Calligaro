package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.JsonToLeaderCard;
import it.polimi.ingsw.server.MessageToClient.ResourceToPay;
import it.polimi.ingsw.server.MessageToClient.SelectResourceIn;
import it.polimi.ingsw.server.MessageToClient.SelectResourceOut;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.model.Dashboard;
import it.polimi.ingsw.server.model.DevelopmentCard;
import it.polimi.ingsw.server.model.exceptions.*;
import it.polimi.ingsw.server.model.leaderCards.LeaderCard;

/**
 * Enter in this state when a production is ended, player can now:
 * - Start another production (card, base or extra)
 * - Activate (or discard) leader cards
 * - End the turn
 */
public class ProductionState extends TurnState {

    public ProductionState(Controller controller) {
        super(controller);
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

    @Override
    public void end() {
        getController().nextTurn();
        getController().setCurrentState(new SelectionState(getController()));
    }

    @Override
    public void completeTurn() {

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
