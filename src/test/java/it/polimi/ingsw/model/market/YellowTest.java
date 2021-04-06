package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.MarketStrategy;
import it.polimi.ingsw.model.benefit.Benefit;
import it.polimi.ingsw.model.benefit.Resource;
import it.polimi.ingsw.model.stubs.MarketStrategyStub;
import org.junit.jupiter.api.Test;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class YellowTest {
    @Test
    void returnCoin(){
        MarketStrategyStub strategy = new MarketStrategyStub();
        Stack<MarketStrategy> strategyStack = new Stack<MarketStrategy>();
        strategyStack.push(strategy);

        Yellow yellow = new Yellow();
        Benefit result = yellow.getBenefit(strategyStack);
        assertTrue(result.equals(Resource.COIN));
    }

    @Test
    void isWhite(){
        Yellow yellow = new Yellow();
        assertFalse(yellow.isWhite());
    }

    @Test
    void dontTouchMyStack(){
        MarketStrategyStub strategy = new MarketStrategyStub();
        Stack<MarketStrategy> strategyStack = new Stack<MarketStrategy>();
        strategyStack.push(strategy);

        Yellow yellow = new Yellow();
        Benefit result = yellow.getBenefit(strategyStack);
        assertTrue(strategyStack.size() == 1 && strategyStack.pop().equals(strategy));
    }
}