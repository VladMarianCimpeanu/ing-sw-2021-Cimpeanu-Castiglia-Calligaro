package it.polimi.ingsw.model.leaderCards;

import it.polimi.ingsw.model.Dashboard;
import it.polimi.ingsw.model.ExtraSlot;
import it.polimi.ingsw.model.MarketStrategy;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.benefit.Resource;
import it.polimi.ingsw.model.exceptions.CardActivationException;

import java.util.ArrayList;

/**
 * A leader card that gives 2 extra slot inside Warehouse Depot for a fixed resource.
 */
public class DepotSkill extends LeaderCard{
    //Always 2 space more in the depot of specified resource
    //Indipendent from the the actual shelves(they can be filled in at any time during the game)
    public DepotSkill(ArrayList<Requirement> requirements, Resource resource, int victoryPointsAmount, int ID) {
        super(requirements, resource, victoryPointsAmount, ID);
    }

    /**
     * add a SlotExtra into the WarehouseDepot's related ArrayList
     * @param player
     * @exception CardActivationException
     */
    @Override
    public void activeCard(Player player) throws CardActivationException {
        if(player == null || !isSatisfied(player.getDashboard())) throw new CardActivationException();

        Dashboard dashboard = player.getDashboard();
        dashboard.getWarehouseDepot().addExtraSlot(getResource());
    }
}
