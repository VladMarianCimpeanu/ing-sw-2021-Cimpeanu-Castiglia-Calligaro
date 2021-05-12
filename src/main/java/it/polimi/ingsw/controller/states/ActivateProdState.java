package it.polimi.ingsw.controller.states;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Dashboard;
import it.polimi.ingsw.model.exceptions.NoProductionAvailableException;

/**
 * Finalize the production when all the resources has been payed.
 */
public class ActivateProdState extends TurnState {

    public ActivateProdState(Controller controller) {
        super(controller);
    }

    @Override
    public void activateProduction() {
        Controller controller = getController();
        Dashboard dashboard = controller.getCurrentPlayer().getDashboard();

        try {
            dashboard.activateProduction();
        } catch (NoProductionAvailableException e) {
            e.printStackTrace();
        }finally {
            controller.setCurrentState(new ProductionState(controller));
        }
    }
}
