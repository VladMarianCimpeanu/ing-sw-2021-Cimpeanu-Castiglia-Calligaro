package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.MessageToClient.ResourceToPay;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.model.Dashboard;
import it.polimi.ingsw.server.model.benefit.Resource;
import it.polimi.ingsw.server.model.exceptions.NotEnoughResourcesException;
import it.polimi.ingsw.server.model.exceptions.ProductionStartedException;
import it.polimi.ingsw.server.model.exceptions.ProductionUsedException;
import it.polimi.ingsw.server.model.exceptions.ResourceCostException;

import java.util.HashMap;
import java.util.Map;

/**
 * After selecting the resources in input, the player can select
 * the resource wanted in output.
 */
public class BaseProdOutState extends TurnState {
    private Resource res1In;
    private Resource res2In;
    private boolean firstProduction;

    public BaseProdOutState(Controller controller, Resource res1In, Resource res2In, boolean firstProduction) {
        super(controller);
        this.res1In = res1In;
        this.res2In = res2In;
        this.firstProduction = firstProduction;
    }

    @Override
    public void selectOutput(Resource resourceOut) {
        Controller controller = getController();
        Dashboard dashboard = controller.getCurrentPlayer().getDashboard();
        Map<Resource, Integer> input = new HashMap<>();
        if(res1In.equals(res2In)){
            input.put(res1In, 2);
        }else{
            input.put(res1In, 1);
            input.put(res2In, 1);
        }
        try {
            dashboard.selectBaseProduction(input,resourceOut);
            controller.sendMessage(new ResourceToPay(input));
            controller.setCurrentState(new CardProdState(controller));
        } catch (NotEnoughResourcesException e) {
            resetState(controller, ErrorMessage.notEnoughResources);
        } catch (ResourceCostException e) {
            resetState(controller, ErrorMessage.wrongSizeInput);
        } catch (ProductionStartedException e) {
            resetState(controller, ErrorMessage.productionAlreadyStarted);
        } catch (ProductionUsedException e) {
            resetState(controller, ErrorMessage.productionUsed);
        }
    }

    private void resetState(Controller controller, ErrorMessage error) {
        controller.sendError(error.toString());
        if (firstProduction) controller.setCurrentState(new SelectionState(controller));
        else controller.setCurrentState(new ProductionState(controller));
    }
    @Override
    public void completeTurn() {

    }
}
