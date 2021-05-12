package it.polimi.ingsw.server.model.market;

import it.polimi.ingsw.server.model.MarketStrategy;
import it.polimi.ingsw.server.model.benefit.Benefit;

import java.util.Stack;

/**
 * This class represents the marbles located inside the market
 * Each one, if picked, can give back a benefit to the player
 */
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
