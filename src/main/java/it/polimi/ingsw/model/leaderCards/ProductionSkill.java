package it.polimi.ingsw.model.leaderCards;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.benefit.Resource;

import java.util.ArrayList;

public class ProductionSkill extends LeaderCard{
    public ProductionSkill(ArrayList<Requirement> requirements, Resource resource, int victoryPointsAmount) {
        super(requirements, resource, victoryPointsAmount);
    }


    /**
     * add ExtraProduction object to Dashboard's related ArrayList
     * @param player: Dashboard referred to
     */
    @Override
    public void activeCard(Player player) {

    }
}
