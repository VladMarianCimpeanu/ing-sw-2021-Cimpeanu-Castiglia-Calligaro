package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.model.Dashboard;
import it.polimi.ingsw.server.model.exceptions.NoProductionAvailableException;

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
