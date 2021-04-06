package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.MarketStrategy;
import it.polimi.ingsw.model.benefit.Benefit;
import it.polimi.ingsw.model.benefit.Resource;
import it.polimi.ingsw.model.stubs.MarketStrategyStub;
import org.junit.jupiter.api.Test;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class WhiteTest {
    @Test
    void getBenefitFromStrategy(){
        MarketStrategyStub strategy = new MarketStrategyStub();
        Stack<MarketStrategy> strategyStack = new Stack<MarketStrategy>();
        strategyStack.push(strategy);
        strategy.resource = Resource.COIN;

        White white = new White();
        Benefit result = white.getBenefit(strategyStack);
        assertTrue(result.equals(Resource.COIN));
    }

    @Test
    void monoStack(){
        MarketStrategyStub strategy = new MarketStrategyStub();
        Stack<MarketStrategy> strategyStack = new Stack<MarketStrategy>();
        strategyStack.push(strategy);
        strategy.resource = Resource.SERVANT;

        White white = new White();
        white.getBenefit(strategyStack);
        assertTrue( strategyStack.empty() );
    }

    @Test
    void getBenefitFromStrategyStack(){
        MarketStrategyStub strategy1 = new MarketStrategyStub();
        MarketStrategyStub strategy2 = new MarketStrategyStub();
        Stack<MarketStrategy> strategyStack = new Stack<MarketStrategy>();
        strategyStack.push(strategy1);
        strategyStack.push(strategy2);
        strategy1.resource = Resource.SHIELD;
        strategy2.resource = Resource.SERVANT;

        White white = new White();
        Benefit result = white.getBenefit(strategyStack);
        assertTrue(result.equals(Resource.SERVANT));
    }

    @Test
    void popStack(){
        MarketStrategyStub strategy1 = new MarketStrategyStub();
        MarketStrategyStub strategy2 = new MarketStrategyStub();
        Stack<MarketStrategy> strategyStack = new Stack<MarketStrategy>();
        strategyStack.push(strategy1);
        strategyStack.push(strategy2);
        strategy1.resource = Resource.SHIELD;
        strategy2.resource = Resource.COIN;

        White white = new White();
        white.getBenefit(strategyStack);
        assertTrue(strategyStack.size() == 1 && strategyStack.pop().equals(strategy1));
    }

    @Test
    void noStrategyGetBenefit(){
        Stack<MarketStrategy> strategyStack = new Stack<MarketStrategy>();

        White white = new White();
        Benefit result = white.getBenefit(strategyStack);
        assertTrue(result == null);
    }

    @Test
    void emptyStrategy(){
        Stack<MarketStrategy> strategyStack = new Stack<MarketStrategy>();

        White white = new White();
        white.getBenefit(strategyStack);
        assertTrue(strategyStack.empty());
    }

    @Test
    void isWhite(){
        White white = new White();
        assertTrue(white.isWhite());
    }
}