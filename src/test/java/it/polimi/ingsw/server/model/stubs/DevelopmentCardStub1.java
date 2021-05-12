package it.polimi.ingsw.server.model.stubs;

import it.polimi.ingsw.server.model.DevelopmentCard;
import it.polimi.ingsw.server.model.benefit.Benefit;
import it.polimi.ingsw.server.model.benefit.Resource;

import java.util.Map;

public class DevelopmentCardStub1 extends DevelopmentCard{
    public DevelopmentCardStub1() {
        super(null);        //?
    }

    @Override
    public Map<Resource, Integer> getResourceIn() { return null; }

    @Override
    public Map<Resource, Integer> getResourceCost() {
        return null;
    }

    @Override
    public Map<Benefit, Integer> getBenefitsOut() {
        return null;
    }
}
