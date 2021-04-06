package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.MarketStrategy;
import it.polimi.ingsw.model.benefit.Benefit;
import it.polimi.ingsw.model.benefit.Resource;
import it.polimi.ingsw.model.stubs.MarketStrategyStub;
import org.junit.jupiter.api.Test;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class GreyTest {
    @Test
    void returnStone(){
        MarketStrategyStub strategy = new MarketStrategyStub();
        Stack<MarketStrategy> strategyStack = new Stack<MarketStrategy>();
        strategyStack.push(strategy);

        Grey grey = new Grey();
        Benefit result = grey.getBenefit(strategyStack);
        assertTrue(result.equals(Resource.STONE));
    }

    @Test
    void isWhite(){
        Grey grey = new Grey();
        assertFalse(grey.isWhite());
    }

    @Test
    void dontTouchMyStack(){
        MarketStrategyStub strategy = new MarketStrategyStub();
        Stack<MarketStrategy> strategyStack = new Stack<MarketStrategy>();
        strategyStack.push(strategy);

        Grey grey = new Grey();
        Benefit result = grey.getBenefit(strategyStack);
        assertTrue(strategyStack.size() == 1 && strategyStack.pop().equals(strategy));
    }
}