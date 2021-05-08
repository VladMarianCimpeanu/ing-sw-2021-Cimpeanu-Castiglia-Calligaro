package it.polimi.ingsw.controller.states;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Dashboard;
import it.polimi.ingsw.model.DevelopmentCard;
import it.polimi.ingsw.model.exceptions.*;

public class ProductionState extends TurnState {

    public ProductionState(Controller controller) {
        super(controller);
    }

    @Override
    public void activateDevCard(int deckIndex) {
        Controller controller = getController();
        Dashboard dashboard = controller.getCurrentPlayer().getDashboard();
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
        controller.setCurrentState(new BaseProdInState(controller));
    }

    @Override
    public void activateExtra(int id) {
        Controller controller = getController();
        controller.setCurrentState(new ExtraProdOutState(controller));
    }
}
