package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.EchoServerClientHandler;
import it.polimi.ingsw.server.MultiEchoServer;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.stubs.EchoStub;
import it.polimi.ingsw.server.model.Identity;
import it.polimi.ingsw.server.model.MarketStrategy;
import it.polimi.ingsw.server.model.benefit.Resource;
import it.polimi.ingsw.server.model.exceptions.InvalidDirectionSelection;
import it.polimi.ingsw.server.model.exceptions.NoSuchStrategyException;
import it.polimi.ingsw.server.model.exceptions.OutOfBoundColumnsException;
import it.polimi.ingsw.server.model.exceptions.OutOfBoundRowException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MarketStrategyStateTest {

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
    void wrongCommand(){
        controller.setCurrentState(new MarketStrategyState(controller,3));
        controller.getCurrentState().end();
        assertEquals(controller.getCurrentState().getClass(), MarketStrategyState.class);
    }

    @Test
    void addStrategy() throws NoSuchStrategyException, InvalidDirectionSelection, OutOfBoundRowException, OutOfBoundColumnsException {
        controller.getCurrentPlayer().addMarketStrategy(new MarketStrategy(Resource.COIN,1));
        int white = controller.getCurrentPlayer().goToMarket("column", 0);
        if(white == 0) controller.setCurrentState(new MarketState(controller));
        else controller.setCurrentState(new MarketStrategyState(controller, white));

        for(int i = 0; i < white; i++){
            controller.getCurrentState().addStrategy(1);
        }
        assertEquals(controller.getCurrentState().getClass(), MarketState.class);
    }

}