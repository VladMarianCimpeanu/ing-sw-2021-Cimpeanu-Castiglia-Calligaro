package it.polimi.ingsw.model.stubs;

import it.polimi.ingsw.model.ExtraProduction;
import it.polimi.ingsw.model.benefit.Benefit;
import it.polimi.ingsw.model.benefit.Faith;
import it.polimi.ingsw.model.benefit.Resource;

import java.util.HashMap;
import java.util.Map;

public class ExtraProductionStub extends ExtraProduction {
    private int id;
    private Resource resource;

    public ExtraProductionStub(int id, Resource resource) {
        super(null);
        this.id = id;
        this.resource = resource;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExtraProductionStub that = (ExtraProductionStub) o;
        return id == that.id;
    }

    @Override
    public Resource getResourceIn() {
        return resource;
    }

    @Override
    public Map<Benefit, Integer> getResourceOut(Resource resourceOut) {
        HashMap<Benefit, Integer> map = new HashMap<Benefit, Integer>();
        map.put(resourceOut,1);
        map.put(Faith.giveFaith(),1);

        return map;
    }
}
