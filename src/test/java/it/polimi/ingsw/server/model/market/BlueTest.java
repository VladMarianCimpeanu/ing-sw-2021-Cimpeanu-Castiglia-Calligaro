package it.polimi.ingsw.server.model.market;

import it.polimi.ingsw.server.model.MarketStrategy;
import it.polimi.ingsw.server.model.benefit.Benefit;
import it.polimi.ingsw.server.model.benefit.Resource;
import it.polimi.ingsw.server.model.stubs.MarketStrategyStub;
import org.junit.jupiter.api.Test;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class BlueTest {
    @Test
    void returnShield(){
        MarketStrategyStub strategy = new MarketStrategyStub();
        Stack<MarketStrategy> strategyStack = new Stack<MarketStrategy>();
        strategyStack.push(strategy);

        Blue blue = new Blue();
        Benefit result = blue.getBenefit(strategyStack);
        assertTrue(result.equals(Resource.SHIELD));
    }

    @Test
    void isWhite(){
        Blue blue = new Blue();
        assertFalse(blue.isWhite());
    }

    @Test
    void dontTouchMyStack(){
        MarketStrategyStub strategy = new MarketStrategyStub();
        Stack<MarketStrategy> strategyStack = new Stack<MarketStrategy>();
        strategyStack.push(strategy);

        Blue blue = new Blue();
        Benefit result = blue.getBenefit(strategyStack);
        assertTrue(strategyStack.size() == 1 && strategyStack.pop().equals(strategy));
    }
}