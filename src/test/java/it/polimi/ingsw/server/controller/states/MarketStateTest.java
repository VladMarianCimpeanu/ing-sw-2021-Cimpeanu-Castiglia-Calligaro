package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.stubs.EchoStub;
import it.polimi.ingsw.server.model.Identity;
import it.polimi.ingsw.server.model.benefit.Resource;
import it.polimi.ingsw.server.model.exceptions.InvalidDirectionSelection;
import it.polimi.ingsw.server.model.exceptions.OutOfBoundColumnsException;
import it.polimi.ingsw.server.model.exceptions.OutOfBoundRowException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MarketStateTest {
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
    void wrongCommand(){
        controller.setCurrentState(new MarketState(controller));
        controller.getCurrentState().goToMarket("row",1);
        assertEquals(controller.getCurrentState().getClass(), MarketState.class);
    }

    @Test
    void putExtra() throws InvalidDirectionSelection, OutOfBoundRowException, OutOfBoundColumnsException {
        controller.setCurrentState(new SelectionState(controller));
        controller.getCurrentState().goToMarket("column", 2);

        ArrayList<Resource> res = controller.getCurrentPlayer().getReceivedFromMarket();
        for(Resource r: res){
            controller.getCurrentPlayer().getDashboard().getWarehouseDepot().addExtraSlot(r, 17);
            controller.getCurrentState().putExtraSlot(r);
        }

        assertEquals(controller.getCurrentState().getClass(), EndTurnState.class);
    }

    @Test
    void discard() throws InvalidDirectionSelection, OutOfBoundRowException, OutOfBoundColumnsException {
        controller.setCurrentState(new SelectionState(controller));
        controller.getCurrentState().goToMarket("column", 2);

        ArrayList<Resource> res = controller.getCurrentPlayer().getReceivedFromMarket();
        for(Resource r: res){
            controller.getCurrentState().discardRes(r);
        }

        assertEquals(controller.getCurrentState().getClass(), EndTurnState.class);
    }

}