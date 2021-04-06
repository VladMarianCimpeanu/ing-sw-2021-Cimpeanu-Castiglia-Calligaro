package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.MarketStrategy;
import it.polimi.ingsw.model.benefit.Benefit;
import it.polimi.ingsw.model.benefit.Resource;
import it.polimi.ingsw.model.stubs.MarketStrategyStub;
import org.junit.jupiter.api.Test;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class PurpleTest {
    @Test
    void returnServant(){
        MarketStrategyStub strategy = new MarketStrategyStub();
        Stack<MarketStrategy> strategyStack = new Stack<MarketStrategy>();
        strategyStack.push(strategy);

        Purple purple = new Purple();
        Benefit result = purple.getBenefit(strategyStack);
        assertTrue(result.equals(Resource.SERVANT));
    }

    @Test
    void isWhite(){
        Purple purple = new Purple();
        assertFalse(purple.isWhite());
    }

    @Test
    void dontTouchMyStack(){
        MarketStrategyStub strategy = new MarketStrategyStub();
        Stack<MarketStrategy> strategyStack = new Stack<MarketStrategy>();
        strategyStack.push(strategy);

        Purple purple = new Purple();
        Benefit result = purple.getBenefit(strategyStack);
        assertTrue(strategyStack.size() == 1 && strategyStack.pop().equals(strategy));
    }

}