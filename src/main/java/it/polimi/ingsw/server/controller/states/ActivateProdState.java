package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.model.Dashboard;
import it.polimi.ingsw.server.model.exceptions.GameEndedException;
import it.polimi.ingsw.server.model.exceptions.NoProductionAvailableException;

/**
 * Finalize the production when all the resources has been payed.
 */
public class ActivateProdState extends TurnState {

    public ActivateProdState(Controller controller) {
        super(controller);
    }

    /**
     * Activate the last phase of a generic production putting produced resources in the strongbox and adding faith
     * points (whether is specified by the production) to the current turn's player
     */
    @Override
    public void activateProduction(){
        Controller controller = getController();
        Dashboard dashboard = controller.getCurrentPlayer().getDashboard();
        try {
            int faithPoints = dashboard.activateProduction();
            //add the faithPoints obtained by the production activation to the faithPath
            controller.getCurrentPlayer().addFaithPoint(faithPoints);
        } catch (NoProductionAvailableException e) {
            e.printStackTrace();
        } catch (GameEndedException gameEndedException) {
            endGame();
        } finally {
            controller.setCurrentState(new ProductionState(controller));
        }
    }

    /**
     * It adds all the resources produced in this turn to the strongbox and makes the current player's dashboard forget
     * all the productions used in the current turn.
     * Then notify the controller to trigger the next turn.
     */
    @Override
    public void completeTurn() {
        getController().getCurrentPlayer().getDashboard().getStrongbox().addProduced();
        getController().getCurrentPlayer().getDashboard().refreshState();
        getController().nextTurn();
    }
}
