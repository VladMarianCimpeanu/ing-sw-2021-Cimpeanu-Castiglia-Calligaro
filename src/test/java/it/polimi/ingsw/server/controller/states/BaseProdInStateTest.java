package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.stubs.EchoStub;
import it.polimi.ingsw.server.model.Identity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static it.polimi.ingsw.server.model.benefit.Resource.*;
import static org.junit.jupiter.api.Assertions.*;

class BaseProdInStateTest {
    private Controller controller;
    @BeforeEach
    void init(){
        ArrayList<Identity> identities = new ArrayList<>();
        identities.add(new Identity("A"));
        identities.add(new Identity("B"));
        ClientHandler handler = new EchoStub();
        Server.addNickname("A", handler);
        Server.addNickname("B", handler);
        controller = new Controller(identities);
        controller.setCurrentUser("A");
    }

    @Test
    void selectInput() {
        BaseProdInState state = new BaseProdInState(controller, true);
        state.selectInput(SHIELD, COIN);
        assertEquals(BaseProdOutState.class, controller.getCurrentState().getClass());
    }

    @Test
    void completeTurn1() {
        BaseProdInState state = new BaseProdInState(controller, true);
        state.completeTurn();
        assertEquals(SelectionState.class, controller.getCurrentState().getClass());
        assertEquals(controller.getCurrentPlayer().getNickName(), "B");

    }

    @Test
    void completeTurn2() {
        BaseProdInState state = new BaseProdInState(controller, false);
        state.completeTurn();
        assertEquals(SelectionState.class, controller.getCurrentState().getClass());
        assertEquals(controller.getCurrentPlayer().getNickName(), "B");
    }
}