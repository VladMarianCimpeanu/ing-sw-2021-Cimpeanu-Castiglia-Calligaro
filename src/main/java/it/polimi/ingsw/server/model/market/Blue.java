package it.polimi.ingsw.server.model.market;

import it.polimi.ingsw.server.model.MarketStrategy;
import it.polimi.ingsw.server.model.benefit.Benefit;

import java.util.Stack;

import static it.polimi.ingsw.server.model.benefit.Resource.SHIELD;

/**
 * Identify Blue Marbles
 */

public class Blue extends Marble {
    /**
     *
     * @param marketStrategies stack containing market strategies
     * @return SHIELD
     */
    @Override
    public Benefit getBenefit(Stack<MarketStrategy> marketStrategies) {
        return SHIELD;
    }

    @Override
    public String toString(){
        return "BLUE";
    }
}
