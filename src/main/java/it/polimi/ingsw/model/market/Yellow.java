package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.benefit.Benefit;

import java.util.Stack;

import static it.polimi.ingsw.model.benefit.Resource.COIN;

public class Yellow extends Marble{
    /**
     *
     * @param marketStrategies stack containing market strategies
     * @return a coin
     */
    @Override
    public Benefit getBenefit(Stack<MarketStrategy> marketStrategies) {
        return COIN;
    }
}
