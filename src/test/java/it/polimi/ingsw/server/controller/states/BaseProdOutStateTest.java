package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.EchoServerClientHandler;
import it.polimi.ingsw.server.MultiEchoServer;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.stubs.EchoStub;
import it.polimi.ingsw.server.model.Dashboard;
import it.polimi.ingsw.server.model.Identity;
import it.polimi.ingsw.server.model.Strongbox;
import it.polimi.ingsw.server.model.WarehouseDepot;
import it.polimi.ingsw.server.model.exceptions.ExistingResourceException;
import it.polimi.ingsw.server.model.exceptions.InvalidResourceException;
import it.polimi.ingsw.server.model.exceptions.InvalidShelfPosition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static it.polimi.ingsw.server.model.benefit.Resource.*;
import static org.junit.jupiter.api.Assertions.*;

class BaseProdOutStateTest {
    private Controller controller;
    @BeforeEach
    void init(){
        ArrayList<Identity> identities = new ArrayList<>();
        identities.add(new Identity("A"));
        identities.add(new Identity("B"));
        EchoServerClientHandler handler = new EchoStub();
        MultiEchoServer.addNickname("A", handler);
        MultiEchoServer.addNickname("B", handler);
        controller = new Controller(identities);
        controller.setCurrentUser("A");
    }

    @Test
    void selectOutput() {
        BaseProdOutState state = new BaseProdOutState(controller, SHIELD, SERVANT, true);
        WarehouseDepot depot = controller.getCurrentPlayer().getDashboard().getWarehouseDepot();
        try {
            depot.addResource(1, 1, SHIELD);
            depot.addResource(2, 1, SERVANT);
        } catch (InvalidShelfPosition | InvalidResourceException | ExistingResourceException invalidShelfPosition) {
            invalidShelfPosition.printStackTrace();
        }
        state.selectOutput(COIN);
        Dashboard dashboard = controller.getCurrentPlayer().getDashboard();
        assertTrue(dashboard.getBenefitsToProduce().containsKey(COIN));
        assertEquals(CardProdState.class, controller.getCurrentState().getClass());
    }

    @Test
    void completeTurn1() {
        BaseProdOutState state = new BaseProdOutState(controller, SHIELD, SERVANT, true);
        state.completeTurn();
        assertEquals(SelectionState.class, controller.getCurrentState().getClass());
        assertEquals(controller.getCurrentPlayer().getNickName(), "B");

    }

    @Test
    void completeTurn2() {
        BaseProdOutState state = new BaseProdOutState(controller, SHIELD, SERVANT, true);
        state.completeTurn();
        assertEquals(SelectionState.class, controller.getCurrentState().getClass());
        assertEquals(controller.getCurrentPlayer().getNickName(), "B");
    }
}