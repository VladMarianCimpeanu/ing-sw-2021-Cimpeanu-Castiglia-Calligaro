package it.polimi.ingsw.model.leaderCards;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.benefit.Resource;

import java.util.ArrayList;

public class DepotSkill extends LeaderCard{
    //Always 2 space more in the depot of specified resource
    //Indipendent from the the actual shelves(they can be filled in at any time during the game)
    public DepotSkill(ArrayList<Requirement> requirements, Resource resource, int victoryPointsAmount) {
        super(requirements, resource, victoryPointsAmount);
    }

    /**
     * add a SlotExtra into the WarehouseDepot's related ArrayList
     * @param player
     */
    @Override
    public void activeCard(Player player) {

    }
}
