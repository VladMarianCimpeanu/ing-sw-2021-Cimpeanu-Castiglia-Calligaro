package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.benefit.Benefit;
import it.polimi.ingsw.model.benefit.Faith;

import java.util.Stack;

public class Red extends Marble{
    /**
     *
     * @param marketStrategies stack containing market strategies
     * @return a Faith object
     */
    @Override
    public Benefit getBenefit(Stack<MarketStrategy> marketStrategies) {
        return new Faith();
    }
}
