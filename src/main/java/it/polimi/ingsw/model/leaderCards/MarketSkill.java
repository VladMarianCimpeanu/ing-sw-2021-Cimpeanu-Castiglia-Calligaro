package it.polimi.ingsw.model.leaderCards;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.benefit.Resource;

import java.util.ArrayList;

public class MarketSkill extends LeaderCard{

    public MarketSkill(ArrayList<Requirement> requirements, Resource resource, int victoryPointsAmount) {
        super(requirements, resource, victoryPointsAmount);
    }

    /**
     * add MarketStrategy to player's related ArrayList
     * @param player
     */
    @Override
    public void activeCard(Player player) {

    }

}
