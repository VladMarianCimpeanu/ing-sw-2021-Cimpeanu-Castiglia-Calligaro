package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.stubs.EchoStub;
import it.polimi.ingsw.server.model.Identity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class EndTurnStateTest {
    private Controller controller;

    @BeforeEach
    void init(){
        ArrayList<Identity> identities = new ArrayList<>();
        identities.add(new Identity("dani"));
        identities.add(new Identity("nick"));
        ClientHandler handler = new EchoStub();
        Server.addNickname("dani", handler);
        Server.addNickname("nick", handler);
        controller = new Controller(identities);
        controller.setCurrentUser("dani");
    }

    @Test
    void activateLeaderCard(){
        int id = controller.getCurrentPlayer().getLeaderCards().get(0).getID();
        controller.setCurrentState(new EndTurnState(controller));
        controller.getCurrentState().activateLeaderCard(id);
        assertEquals(controller.getCurrentState().getClass(), EndTurnState.class);
    }

    @Test
    void discardLeaderCard(){
        int id = controller.getCurrentPlayer().getLeaderCards().get(0).getID();
        controller.setCurrentState(new EndTurnState(controller));
        controller.getCurrentState().discardLeaderCard(id);
        assertEquals(controller.getCurrentState().getClass(), EndTurnState.class);
    }

    @Test
    void end(){
        controller.setCurrentState(new EndTurnState(controller));
        controller.getCurrentState().end();
        assertEquals(controller.getCurrentState().getClass(), SelectionState.class);
    }
}