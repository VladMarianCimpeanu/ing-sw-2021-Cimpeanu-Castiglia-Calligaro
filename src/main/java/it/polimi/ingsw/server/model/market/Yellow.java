package it.polimi.ingsw.server.model.market;

import it.polimi.ingsw.server.model.MarketStrategy;
import it.polimi.ingsw.server.model.benefit.Benefit;

import java.util.Stack;

import static it.polimi.ingsw.server.model.benefit.Resource.COIN;

/**
 * Identify Yellow Marbles
 */

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
    @Override
    public String toString(){
        return "YELLOW";
    }
}
