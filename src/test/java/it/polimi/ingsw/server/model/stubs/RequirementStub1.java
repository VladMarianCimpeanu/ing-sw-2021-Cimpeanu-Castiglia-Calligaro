package it.polimi.ingsw.server.model.stubs;

import it.polimi.ingsw.server.model.Dashboard;
import it.polimi.ingsw.server.model.leaderCards.Requirement;

public class RequirementStub1 implements Requirement {
    private boolean satisfied;
    public RequirementStub1(boolean satisfied){
        this.satisfied = satisfied;
    }

    @Override
    public boolean isSatisfied(Dashboard dashboard) {
        return satisfied;
    }
}
