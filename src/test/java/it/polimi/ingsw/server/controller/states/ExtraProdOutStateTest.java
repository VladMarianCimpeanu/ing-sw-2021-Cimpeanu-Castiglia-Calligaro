package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.EchoServerClientHandler;
import it.polimi.ingsw.server.JsonToLeaderCard;
import it.polimi.ingsw.server.MultiEchoServer;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.stubs.EchoStub;
import it.polimi.ingsw.server.model.Discount;
import it.polimi.ingsw.server.model.ExtraProduction;
import it.polimi.ingsw.server.model.Identity;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static it.polimi.ingsw.server.model.Color.*;
import static it.polimi.ingsw.server.model.benefit.Resource.*;
import static org.junit.jupiter.api.Assertions.*;

class ExtraProdOutStateTest {
    private Controller controller;

    @BeforeEach
    void init() throws GameEndedException {
        ArrayList<Identity> identities = new ArrayList<>();
        identities.add(new Identity("A"));
        identities.add(new Identity("B"));
        EchoServerClientHandler handler = new EchoStub();
        MultiEchoServer.addNickname("A", handler);
        MultiEchoServer.addNickname("B", handler);
        controller = new Controller(identities);
        controller.setCurrentUser("A");
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
        try {
            player.drawDevelopmentCard(YELLOW, 1, new ArrayList<Discount>());
            player.completePayment();
            player.placeDevelopmentCard(1);
            player.drawDevelopmentCard(YELLOW, 2, new ArrayList<Discount>());
            player.completePayment();
            player.placeDevelopmentCard(1);
        } catch (WrongLevelException | NotEnoughResourcesException | NoCardException | InvalidDiscountException e) {
            e.printStackTrace();
        }
        try {
            JsonToLeaderCard.getLeaderCard(13).activeCard(player);
        } catch (NoCardException | CardActivationException e) {
            e.printStackTrace();
        }


    }
    @Test
    void selectOutput() {
        ExtraProdOutState state = new ExtraProdOutState(controller, 13, true);
        controller.setCurrentState(state);
        state.selectOutput(COIN);
        assertEquals(CardProdState.class, controller.getCurrentState().getClass());
        assertEquals(1, controller.getCurrentPlayer().getDashboard().getResourcesToPay().size());
        assertTrue(controller.getCurrentPlayer().getDashboard().getResourcesToPay().contains(SHIELD));
    }

    @Test
    void completeTurn() {
        ExtraProdOutState state = new ExtraProdOutState(controller, 13, true);
        state.completeTurn();
        assertEquals(SelectionState.class, controller.getCurrentState().getClass());
        assertEquals("B", controller.getCurrentPlayer().getNickName());

    }
}