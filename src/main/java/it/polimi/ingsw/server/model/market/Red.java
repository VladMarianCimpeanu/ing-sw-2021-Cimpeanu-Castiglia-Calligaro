package it.polimi.ingsw.server.model.market;

import it.polimi.ingsw.server.model.MarketStrategy;
import it.polimi.ingsw.server.model.benefit.Benefit;
import it.polimi.ingsw.server.model.benefit.Faith;

import java.util.Stack;

/**
 * Identify Red Marbles
 */

public class Red extends Marble{
    /**
     *
     * @param marketStrategies stack containing market strategies
     * @return a Faith object
     */
    @Override
    public Benefit getBenefit(Stack<MarketStrategy> marketStrategies) {
        return Faith.giveFaith();
    }
    @Override
    public String toString(){
        return "RED";
    }
}
