package it.polimi.ingsw.model.stubs;

import it.polimi.ingsw.model.MarketStrategy;
import it.polimi.ingsw.model.benefit.Benefit;
import it.polimi.ingsw.model.market.Market;

import java.util.ArrayList;
import java.util.Stack;

public class MarketStub1 extends Market {
    private boolean passedStrategies;
    public MarketStub1(){
        super();
        passedStrategies = false;
    }
    @Override
    public int selColumn(int column){
        return 1;
    }
    @Override
    public int selRow(int row){
        return 2;
    }
    @Override
    public ArrayList<Benefit> convertMarbles(Stack<MarketStrategy> marketStrategies) throws NullPointerException{
       passedStrategies = true;
       return new ArrayList<>();
    }

    public boolean isPassedStrategies() {
        return passedStrategies;
    }
}
