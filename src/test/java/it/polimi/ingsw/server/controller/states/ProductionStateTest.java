package it.polimi.ingsw.server.controller.states;


import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.stubs.EchoStub;
import it.polimi.ingsw.server.model.Identity;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static it.polimi.ingsw.server.model.Color.BLUE;
import static it.polimi.ingsw.server.model.benefit.Resource.*;
import static it.polimi.ingsw.server.model.benefit.Resource.STONE;
import static org.junit.jupiter.api.Assertions.*;

class ProductionStateTest {
    private Controller controller;
    private ProductionState state;
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
        state = new ProductionState(controller);
        controller.setCurrentState(state);
        Player player = controller.getCurrentPlayer();
        try {
            player.getDashboard().getStrongbox().addResource(SHIELD, 20);
            player.getDashboard().getStrongbox().addResource(COIN, 20);
            player.getDashboard().getStrongbox().addResource(SERVANT, 20);
            player.getDashboard().getStrongbox().addResource(STONE, 20);
            player.getDashboard().getStrongbox().addProduced();
        } catch (NegativeQuantityException e) {
            e.printStackTrace();
        }
    }

    @Test
    void activateDevCard() throws GameEndedException {
        try {
            controller.getCurrentPlayer().drawDevelopmentCard(BLUE, 1, new ArrayList<>());
            controller.getCurrentPlayer().completePayment();
            controller.getCurrentPlayer().placeDevelopmentCard(1);
        } catch (NoCardException | InvalidDiscountException | NotEnoughResourcesException | WrongLevelException e) {
            e.printStackTrace();
        }
        state.activateDevCard(1);
        assertEquals(CardProdState.class, controller.getCurrentState().getClass());
    }

    @Test
    void activateBase() {
        state.activateBase();
        assertEquals(BaseProdInState.class, controller.getCurrentState().getClass());
    }

    @Test
    void activateExtra() {
        state.activateExtra(13);
        assertEquals(ExtraProdOutState.class, controller.getCurrentState().getClass());
    }

    @Test
    void end() {
        state.end();
        assertEquals(SelectionState.class, controller.getCurrentState().getClass());
        assertEquals("B", controller.getCurrentPlayer().getNickName());
    }

    @Test
    void completeTurn() {
        state.completeTurn();
        assertEquals(SelectionState.class, controller.getCurrentState().getClass());
        assertEquals("B", controller.getCurrentPlayer().getNickName());
    }

    @Test
    void activateLeaderCard() {
        //Don't we check if the leaderCard is owned by the currentPlayer?
        state.activateLeaderCard(1);    //should throw an Exception because of the requirements not satisfied
    }

    @Test
    void discardLeaderCard() {
        state.discardLeaderCard(controller.getCurrentPlayer().getLeaderCards().get(0).getID());
        assertEquals(EndTurnState.class, controller.getCurrentState().getClass());
    }
}