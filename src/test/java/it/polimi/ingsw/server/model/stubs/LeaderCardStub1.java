package it.polimi.ingsw.server.model.stubs;

import it.polimi.ingsw.server.model.Dashboard;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.exceptions.CardActivationException;
import it.polimi.ingsw.server.model.leaderCards.LeaderCard;

public class LeaderCardStub1 extends LeaderCard {
    private boolean satisfied;

    public LeaderCardStub1(boolean satisfied){
        super(null, null, 0, 0);
        this.satisfied = satisfied;
    }
    @Override
    public void activeCard(Player player) throws CardActivationException {

    }

    @Override
    public boolean isSatisfied(Dashboard db){
        return satisfied;
    }
}
