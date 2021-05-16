package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.EchoServerClientHandler;
import it.polimi.ingsw.server.MultiEchoServer;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.stubs.EchoStub;
import it.polimi.ingsw.server.model.Identity;
import it.polimi.ingsw.server.model.exceptions.NoProductionAvailableException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ActivateProdStateTest {
    private ActivateProdState state;
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
        state = new ActivateProdState(controller);
        controller.setCurrentState(state);
    }

    @Test
    void activateProduction() {
        state.activateProduction();
        assertTrue(controller.getCurrentState() instanceof ProductionState);
    }

    @Test
    void completeTurn() {
        state.completeTurn();
        assertTrue(controller.getCurrentState() instanceof SelectionState);
        assertEquals(controller.getCurrentPlayer().getNickName(), "B");
    }
}