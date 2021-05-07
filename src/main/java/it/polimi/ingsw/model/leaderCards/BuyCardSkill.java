package it.polimi.ingsw.model.leaderCards;

import it.polimi.ingsw.model.Dashboard;
import it.polimi.ingsw.model.Discount;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.benefit.Resource;
import it.polimi.ingsw.model.exceptions.CardActivationException;

import java.util.ArrayList;

/**
 * A leader card that gives a discount of 1 resource to buy development cards.
 */
public class BuyCardSkill extends LeaderCard{

    public BuyCardSkill(ArrayList<Requirement> requirements, Resource resource, int victoryPointsAmount, int ID) {
        super(requirements, resource, victoryPointsAmount, ID);
    }

    /**
     * add a Discount object to Dashboard's related ArrayList
     * @param player : Dashboard referred to
     * @exception CardActivationException
     */
    @Override
    public void activeCard(Player player) throws CardActivationException {
        if(player == null || !isSatisfied(player.getDashboard())) throw new CardActivationException();

        Discount discount = new Discount(getResource(), getID());
        player.addDiscount(discount);
    }

}
