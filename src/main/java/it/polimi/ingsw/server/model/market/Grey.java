package it.polimi.ingsw.server.model.market;

import it.polimi.ingsw.server.model.MarketStrategy;
import it.polimi.ingsw.server.model.benefit.Benefit;

import java.util.Stack;

import static it.polimi.ingsw.server.model.benefit.Resource.STONE;

/**
 * Identify Grey Marbles
 */

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
    @Override
    public String toString(){
        return "GREY";
    }
}
