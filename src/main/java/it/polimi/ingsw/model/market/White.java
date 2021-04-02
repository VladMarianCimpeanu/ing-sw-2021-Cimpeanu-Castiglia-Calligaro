package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.benefit.Benefit;

import java.util.Stack;

public class White extends Marble {
    /**
     * it removes the strategy from the top of the stack and it uses to convert the marble
     * @param marketStrategies stack containing market strategies
     * @return null if marketStrategies is empty, else the benefit indicated by the strategy.
     */
    @Override
    public Benefit getBenefit(Stack<MarketStrategy> marketStrategies) {
        return null;
    }
}
