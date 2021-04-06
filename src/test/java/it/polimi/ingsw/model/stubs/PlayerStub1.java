package it.polimi.ingsw.model.stubs;

import it.polimi.ingsw.model.Dashboard;
import it.polimi.ingsw.model.MarketStrategy;
import it.polimi.ingsw.model.Player;

public class PlayerStub1 extends Player{
    private DashboardStub1 db;
    private MarketStrategy marketStrategycheck;
    public PlayerStub1(){
        super(null, null, null, null);
        db = new DashboardStub1();
        marketStrategycheck = null;
    }

    @Override
    public Dashboard getDashboard() {
        return db;
    }

    @Override
    public void addMarketStrategy(MarketStrategy marketStrategy){
        marketStrategycheck = marketStrategy;
    }

    public MarketStrategy getMarketStrategycheck() {
        return marketStrategycheck;
    }

}
