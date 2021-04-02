package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.benefit.Benefit;

import java.util.Stack;

import static it.polimi.ingsw.model.benefit.Resource.SHIELD;

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
}
