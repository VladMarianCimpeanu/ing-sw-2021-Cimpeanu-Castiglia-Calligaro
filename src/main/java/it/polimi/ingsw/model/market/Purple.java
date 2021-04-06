package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.MarketStrategy;
import it.polimi.ingsw.model.benefit.Benefit;

import java.util.Stack;

import static it.polimi.ingsw.model.benefit.Resource.SERVANT;

public class Purple extends Marble{
    /**
     *
     * @param marketStrategies stack containing market strategies
     * @return a Servant
     */
    @Override
    public Benefit getBenefit(Stack<MarketStrategy> marketStrategies) {
        return SERVANT;
    }
}
