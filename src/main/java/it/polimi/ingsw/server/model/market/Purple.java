package it.polimi.ingsw.server.model.market;

import it.polimi.ingsw.server.model.MarketStrategy;
import it.polimi.ingsw.server.model.benefit.Benefit;

import java.util.Stack;

import static it.polimi.ingsw.server.model.benefit.Resource.SERVANT;

/**
 * Identify Purple Marbles
 */

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
    @Override
    public String toString(){
        return "PURPLE";
    }
}
