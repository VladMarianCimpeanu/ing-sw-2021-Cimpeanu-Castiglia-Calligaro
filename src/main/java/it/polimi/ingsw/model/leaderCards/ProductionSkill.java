package it.polimi.ingsw.model.leaderCards;

import it.polimi.ingsw.model.Dashboard;
import it.polimi.ingsw.model.Discount;
import it.polimi.ingsw.model.ExtraProduction;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.benefit.Resource;
import it.polimi.ingsw.model.exceptions.CardActivationException;

import java.util.ArrayList;

/**
 * A leader card that gives an extra production of 1 resource of your choice and a faith points,
 * the cost is the fixed resource of the leader card.
 */
public class ProductionSkill extends LeaderCard{
    public ProductionSkill(ArrayList<Requirement> requirements, Resource resource, int victoryPointsAmount, int ID) {
        super(requirements, resource, victoryPointsAmount, ID);
    }


    /**
     * add ExtraProduction object to Dashboard's related ArrayList
     * @param player : Dashboard referred to
     * @exception CardActivationException
     */
    @Override
    public void activeCard(Player player) throws CardActivationException {
        if(player == null || !isSatisfied(player.getDashboard())) throw new CardActivationException();

        Dashboard dashboard = player.getDashboard();
        ExtraProduction extraProduction = new ExtraProduction(getResource());
        dashboard.addExtraProduction(extraProduction);
    }
}
