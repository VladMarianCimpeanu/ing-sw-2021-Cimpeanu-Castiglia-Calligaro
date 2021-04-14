package it.polimi.ingsw.model.stubs;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.DevelopmentCard;
import it.polimi.ingsw.model.benefit.Benefit;
import it.polimi.ingsw.model.benefit.Faith;
import it.polimi.ingsw.model.benefit.Resource;

import java.nio.file.NoSuchFileException;
import java.util.HashMap;
import java.util.Map;

public class DevelopmentCardStub extends DevelopmentCard {

    private int id;
    private int level;
    private Resource in;
    private Resource out;

    public DevelopmentCardStub(int id, int level, Resource in, Resource out) throws NoSuchFileException {
        super(null);
        this.id = id;
        this.level = level;
    }

    public DevelopmentCardStub(int id, int level) throws NoSuchFileException {
        this(id,level, Resource.SHIELD, Resource.SERVANT);
    }

    public DevelopmentCardStub(int id) throws NoSuchFileException {
        this(id,1, Resource.SHIELD, Resource.SERVANT);
    }

    @Override
    public Map<Resource, Integer> getResourceIn() {
        HashMap<Resource, Integer> map = new HashMap<>();
        map.put(in,2);
        return map;
    }

    @Override
    public Map<Benefit, Integer> getBenefitsOut() {
        HashMap<Benefit, Integer> map = new HashMap<>();
        map.put(out,1);
        map.put(Faith.giveFaith(),1);

        return map;
    }

    @Override
    public Map<Resource, Integer> getResourceCost() {
        HashMap<Resource, Integer> map = new HashMap<>();
        map.put(Resource.COIN,3);
        return map;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public Color getColor() {
        return Color.BLUE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DevelopmentCardStub that = (DevelopmentCardStub) o;
        return id == that.id;
    }
}
