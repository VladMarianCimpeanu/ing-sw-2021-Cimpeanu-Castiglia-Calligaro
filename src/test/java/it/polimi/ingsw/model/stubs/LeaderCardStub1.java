package it.polimi.ingsw.model.stubs;

import it.polimi.ingsw.model.Dashboard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.leaderCards.LeaderCard;

public class LeaderCardStub1 extends LeaderCard {
    private boolean satisfied;

    public LeaderCardStub1(boolean satisfied){
        super(null, null, 0);
        this.satisfied = satisfied;
    }
    @Override
    public void activeCard(Player player) {

    }

    @Override
    public boolean isSatisfied(Dashboard db){
        return satisfied;
    }
}
