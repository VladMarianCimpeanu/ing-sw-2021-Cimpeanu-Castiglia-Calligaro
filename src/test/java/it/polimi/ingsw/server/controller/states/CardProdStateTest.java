package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.EchoServerClientHandler;
import it.polimi.ingsw.server.MultiEchoServer;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.stubs.EchoStub;
import it.polimi.ingsw.server.model.Dashboard;
import it.polimi.ingsw.server.model.Identity;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.Strongbox;
import it.polimi.ingsw.server.model.benefit.Resource;
import it.polimi.ingsw.server.model.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static it.polimi.ingsw.server.model.benefit.Resource.*;
import static org.junit.jupiter.api.Assertions.*;

class CardProdStateTest {
    private CardProdState state;
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
        state = new CardProdState(controller);
        controller.setCurrentState(state);
    }

    @Test
    void pay1() {
        Player player = controller.getCurrentPlayer();
        Dashboard dashboard = player.getDashboard();
        Strongbox strongbox = dashboard.getStrongbox();

        try {
            strongbox.addResource(SHIELD, 1);
            strongbox.addResource(COIN, 1);
        } catch (NegativeQuantityException e) {
            e.printStackTrace();
        }
        strongbox.addProduced();
        Map<Resource, Integer> resourcesToPay = new HashMap<>();
        resourcesToPay.put(SHIELD, 1);
        resourcesToPay.put(COIN, 1);
        try {
            dashboard.selectBaseProduction(resourcesToPay,SERVANT);
        } catch (NotEnoughResourcesException | ResourceCostException | ProductionStartedException | ProductionUsedException e) {
            e.printStackTrace();
        }
        state.pay(SHIELD, "strongbox");

        assertEquals(CardProdState.class, controller.getCurrentState().getClass());
        assertEquals(1, dashboard.getResourcesToPay().size());
        assertEquals(COIN, dashboard.getResourcesToPay().get(0));
    }

    @Test
    void pay2() {
        Player player = controller.getCurrentPlayer();
        Dashboard dashboard = player.getDashboard();
        Strongbox strongbox = dashboard.getStrongbox();

        try {
            strongbox.addResource(SHIELD, 1);
            strongbox.addResource(COIN, 1);
        } catch (NegativeQuantityException e) {
            e.printStackTrace();
        }
        strongbox.addProduced();
        Map<Resource, Integer> resourcesToPay = new HashMap<>();
        resourcesToPay.put(SHIELD, 1);
        resourcesToPay.put(COIN, 1);
        try {
            dashboard.selectBaseProduction(resourcesToPay,SERVANT);
        } catch (NotEnoughResourcesException | ResourceCostException | ProductionStartedException | ProductionUsedException e) {
            e.printStackTrace();
        }
        state.pay(SHIELD, "strongbox");
        state.pay(COIN, "strongbox");

        assertEquals(ActivateProdState.class, controller.getCurrentState().getClass());
        assertEquals(0, dashboard.getResourcesToPay().size());
    }
    @Test
    void completeTurn() {
        Map<Resource, Integer> res = new HashMap<>();
        res.put(STONE, 1);
        res.put(SHIELD, 1);
        try {
            controller.getCurrentPlayer().getDashboard().getStrongbox().addResource(STONE, 1);
            controller.getCurrentPlayer().getDashboard().getStrongbox().addResource(SHIELD, 1);
            controller.getCurrentPlayer().getDashboard().getStrongbox().addProduced();
            controller.getCurrentPlayer().getDashboard().selectBaseProduction(res, SERVANT);
        } catch (NotEnoughResourcesException | ResourceCostException | ProductionStartedException | ProductionUsedException | NegativeQuantityException e) {
            e.printStackTrace();
        }
        state.completeTurn();
        assertEquals(SelectionState.class, controller.getCurrentState().getClass());
        assertEquals(controller.getCurrentPlayer().getNickName(), "B");
    }
}