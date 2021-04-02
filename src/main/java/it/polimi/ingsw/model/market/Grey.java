package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.benefit.Benefit;

import java.util.Stack;

import static it.polimi.ingsw.model.benefit.Resource.STONE;

public class Grey extends Marble{
    /**
     *
     * @param marketStrategies stack containing market strategies
     * @return a Stone
     */
    @Override
    public Benefit getBenefit(Stack<MarketStrategy> marketStrategies) {
        return STONE;
    }
}
