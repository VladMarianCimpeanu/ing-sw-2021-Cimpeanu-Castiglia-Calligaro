package it.polimi.ingsw.model.stubs;

import it.polimi.ingsw.model.Dashboard;
import it.polimi.ingsw.model.leaderCards.Requirement;

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
