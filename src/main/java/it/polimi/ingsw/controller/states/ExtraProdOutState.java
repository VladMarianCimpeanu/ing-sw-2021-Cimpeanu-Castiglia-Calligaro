package it.polimi.ingsw.controller.states;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Dashboard;
import it.polimi.ingsw.model.ExtraProduction;
import it.polimi.ingsw.model.benefit.Resource;
import it.polimi.ingsw.model.exceptions.InvalidIDExcpetion;
import it.polimi.ingsw.model.exceptions.NotEnoughResourcesException;
import it.polimi.ingsw.model.exceptions.ProductionStartedException;
import it.polimi.ingsw.model.exceptions.ProductionUsedException;


/**
 * In this state the player has activated an extra production and can
 * select the resource wanted in output.
 */
public class ExtraProdOutState extends TurnState {
    private int extraProductionID;

    public ExtraProdOutState(Controller controller, int ID) {
        super(controller);
        extraProductionID = ID;
    }

    @Override
    public void selectOutput(Resource resource) {
        Controller controller = getController();
        Dashboard dashboard = controller.getCurrentPlayer().getDashboard();
        try {
            ExtraProduction extraProduction = dashboard.getExtraProduction(extraProductionID);
            int extraIndex = dashboard.getExtraProductions().indexOf(extraProduction);
            dashboard.selectExtraProduction(extraIndex, resource);
            Resource in = extraProduction.getResourceIn();
            controller.sendSimple("resToPay",in.toString());
            controller.setCurrentState(new CardProdState(controller));
        } catch (NotEnoughResourcesException e) {
            controller.sendError("notEnoughResources");
        } catch (ProductionStartedException e) {
            controller.sendError("productionStarted");
        } catch (ProductionUsedException e) {
            controller.sendError("productionUsed");
        } catch (InvalidIDExcpetion invalidIDExcpetion) {
            controller.sendError("invalidID");
        }

    }
}
