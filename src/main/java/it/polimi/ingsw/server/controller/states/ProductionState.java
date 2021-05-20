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
        try {
            DevelopmentCard card = dashboard.getCardOnTop(deckIndex);
            dashboard.selectCardProduction(deckIndex);
            controller.sendMessage(new ResourceToPay(card.getResourceIn()));
            controller.setCurrentState(new CardProdState(controller));
        } catch (InvalidDeckPositionException e) {
            controller.sendError(ErrorMessage.invalidDeck);
        } catch (NotEnoughResourcesException e) {
            controller.sendError(ErrorMessage.notEnoughResources);
        } catch (NoCardException e) {
            controller.sendError(ErrorMessage.noSuchDevelopmentCard);
        } catch (ProductionStartedException e) {
            controller.sendError(ErrorMessage.productionAlreadyStarted);
        } catch (ProductionUsedException e) {
            controller.sendError(ErrorMessage.productionUsed);
        }
    }

    @Override
    public void activateBase() {
        Controller controller = getController();
        controller.sendMessage(new SelectResourceIn());
        controller.setCurrentState(new BaseProdInState(controller, false));
    }

    @Override
    public void activateExtra(int id) {
        Controller controller = getController();
        controller.sendMessage(new SelectResourceOut());
        controller.setCurrentState(new ExtraProdOutState(controller, id, false));
    }

    @Override
    public void end() {
        endProduction();
        getController().nextTurn();
        getController().setCurrentState(new SelectionState(getController()));
    }

    /**
     * It adds all the resources produced in this turn to the strongbox and makes the current player's dashboard forget
     * all the productions used in the current turn.
     * Then notify the controller to trigger the next turn.
     */
    @Override
    public void completeTurn() {
        endProduction();
        getController().nextTurn();
    }

    @Override
    public void activateLeaderCard(int id) {
        try {
            LeaderCard leaderCard = JsonToLeaderCard.getLeaderCard(id);
            getController().getCurrentPlayer().activateLeaderCard(leaderCard);
            endProduction();
            getController().setCurrentState(new EndTurnState(getController()));
        } catch (NoCardException e) {
            getController().sendError(ErrorMessage.invalidLeaderCardID);
        } catch (RequirementException e) {
            getController().sendError(ErrorMessage.requirementsNotSatisfied);
        }
    }

    @Override
    public void discardLeaderCard(int id) {
        try {
            LeaderCard leaderCard = JsonToLeaderCard.getLeaderCard(id);
            getController().getCurrentPlayer().discardLeaderCard(leaderCard);
            endProduction();
            getController().setCurrentState(new EndTurnState(getController()));
        } catch (NoCardException e) {
            getController().sendError(ErrorMessage.invalidLeaderCardID);
        }
    }

    /**
     * It adds all the resources produced to strongbox and makes the current player's dashboard forget the productions used
     * in the current turn.
     */
    private void endProduction() {
        Dashboard playersDashboard = getController().getCurrentPlayer().getDashboard();
        playersDashboard.getStrongbox().addProduced();
        playersDashboard.refreshProductions();
    }
}
