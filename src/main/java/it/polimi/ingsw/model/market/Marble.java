package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.benefit.Benefit;

import java.util.Stack;

public abstract class Marble {
    /**
     * convert a Marble in benefit
     * @param marketStrategies stack containing market strategies
     * @return benefit obtained through strategy
     */
    public abstract Benefit getBenefit(Stack<MarketStrategy> marketStrategies);
    public boolean isWhite(){
        return false;
    }
}
