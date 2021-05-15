package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.EchoServerClientHandler;
import it.polimi.ingsw.server.MultiEchoServer;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.stubs.EchoStub;
import it.polimi.ingsw.server.model.Identity;
import it.polimi.ingsw.server.model.MarketStrategy;
import it.polimi.ingsw.server.model.benefit.Resource;
import it.polimi.ingsw.server.model.exceptions.NoSuchStrategyException;
import it.polimi.ingsw.server.model.market.Marble;
import it.polimi.ingsw.server.model.market.Purple;
import it.polimi.ingsw.server.model.market.White;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.Socket;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SelectionStateTest {
    private Controller controller;

    @BeforeEach
    void init(){
        ArrayList<Identity> identities = new ArrayList<>();
        identities.add(new Identity("dani"));
        identities.add(new Identity("nick"));
        EchoServerClientHandler handler = new EchoStub();
        MultiEchoServer.addNickname("dani", handler);
        MultiEchoServer.addNickname("nick", handler);
        controller = new Controller(identities);
        controller.setCurrentUser("dani");
    }

    @Test
    void goToMarket(){
        controller.setCurrentState(new SelectionState(controller));
        controller.getCurrentState().goToMarket("row",2);
        if(controller.getCurrentPlayer().isMarketResourcesUnavailable())
            assertEquals(controller.getCurrentState().getClass(), EndTurnState.class);
        assertEquals(controller.getCurrentState().getClass(), MarketState.class);
    }

    @Test
    void goToStrategy() throws NoSuchStrategyException {
        controller.getCurrentPlayer().addMarketStrategy(new MarketStrategy(Resource.COIN,1));
        controller.setCurrentState(new SelectionState(controller));
        controller.getCurrentState().goToMarket("row",1);
        for(Marble m : controller.getGame().getMarket().getSelectedMarbles()){
            if(m.isWhite()) {
                assertEquals(controller.getCurrentState().getClass(), MarketStrategyState.class);
                return;
            }
        }
        assertEquals(controller.getCurrentState().getClass(), MarketState.class);
    }

    @Test
    void activateLeaderCard(){
        int id = controller.getCurrentPlayer().getLeaderCards().get(0).getID();
        controller.setCurrentState(new SelectionState(controller));
        controller.getCurrentState().activateLeaderCard(id);
        assertEquals(controller.getCurrentState().getClass(), SelectionState.class);
    }

    @Test
    void discardLeaderCard(){
        int id = controller.getCurrentPlayer().getLeaderCards().get(0).getID();
        controller.setCurrentState(new SelectionState(controller));
        controller.getCurrentState().discardLeaderCard(id);
        assertEquals(controller.getCurrentState().getClass(), SelectionState.class);
    }
}