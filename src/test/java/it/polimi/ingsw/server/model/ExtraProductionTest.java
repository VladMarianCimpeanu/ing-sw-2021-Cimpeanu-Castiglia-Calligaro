package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.benefit.Benefit;
import it.polimi.ingsw.server.model.benefit.Faith;
import it.polimi.ingsw.server.model.benefit.Resource;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ExtraProductionTest {
    @Test
    void checkResourceIn(){
        ExtraProduction extra = new ExtraProduction(Resource.SHIELD, 0);
        Resource result = extra.getResourceIn();

        assertTrue(result.equals(Resource.SHIELD));
    }

    @Test
    void checkResourceOut(){
        ExtraProduction extra = new ExtraProduction(Resource.COIN, 0);
        Map<Benefit,Integer> result = extra.getResourceOut(Resource.SERVANT);

        assertTrue(result.get(Resource.SERVANT) == 1);
    }

    @Test
    void checkFaithPointsOut(){
        ExtraProduction extra = new ExtraProduction(Resource.SERVANT, 0);
        Map<Benefit,Integer> result = extra.getResourceOut(Resource.SHIELD);
        assertTrue(result.get(Faith.giveFaith()) == 1);
    }

    @Test
    void noOtherResourceOut(){
        ExtraProduction extra = new ExtraProduction(Resource.COIN, 0);
        Map<Benefit,Integer> result = extra.getResourceOut(Resource.STONE);

        assertTrue(!result.containsKey(Resource.COIN) && !result.containsKey(Resource.SHIELD) && !result.containsKey(Resource.SERVANT));
    }
}