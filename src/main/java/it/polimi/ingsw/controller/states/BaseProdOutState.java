package it.polimi.ingsw.controller.states;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Dashboard;
import it.polimi.ingsw.model.benefit.Resource;
import it.polimi.ingsw.model.exceptions.NotEnoughResourcesException;
import it.polimi.ingsw.model.exceptions.ProductionStartedException;
import it.polimi.ingsw.model.exceptions.ProductionUsedException;
import it.polimi.ingsw.model.exceptions.ResourceCostException;

import java.util.HashMap;
import java.util.Map;

/**
 * After selecting the resources in input, the player can select
 * the resource wanted in output.
 */
public class BaseProdOutState extends TurnState {
    private Resource res1In;
    private Resource res2In;

    public BaseProdOutState(Controller controller, Resource res1In, Resource res2In) {
        super(controller);
        this.res1In = res1In;
        this.res2In = res2In;
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
            controller.sendSimple("resToPay", res1In.toString());
            controller.setCurrentState(new CardProdState(controller));
        } catch (NotEnoughResourcesException e) {
            controller.sendError("notEnoughResources");
        } catch (ResourceCostException e) {
            e.printStackTrace();
        } catch (ProductionStartedException e) {
            controller.sendError("productionStarted");
        } catch (ProductionUsedException e) {
            controller.sendError("productionUsed");
        }
    }
}
