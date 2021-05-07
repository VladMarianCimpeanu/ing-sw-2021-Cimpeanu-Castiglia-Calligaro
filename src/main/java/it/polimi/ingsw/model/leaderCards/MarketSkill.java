package it.polimi.ingsw.model.leaderCards;

import it.polimi.ingsw.model.MarketStrategy;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.benefit.Resource;
import it.polimi.ingsw.model.exceptions.CardActivationException;
import it.polimi.ingsw.model.exceptions.NoSuchStrategyException;

import java.util.ArrayList;

/**
 * A leader card that gives a strategy to convert white marbles to the resource of the leader card.
 */
public class MarketSkill extends LeaderCard{

    public MarketSkill(ArrayList<Requirement> requirements, Resource resource, int victoryPointsAmount, int ID) {
        super(requirements, resource, victoryPointsAmount, ID);
    }

    /**
     * add MarketStrategy to player's related ArrayList
     * @param player
     * @exception CardActivationException if the requirement is not satisfied
     */
    @Override
    public void activeCard(Player player) throws CardActivationException {
        if(player == null || !isSatisfied(player.getDashboard())) throw new CardActivationException();
        MarketStrategy strategy = new MarketStrategy(getResource());
        try {
            player.addMarketStrategy(strategy);
        } catch (NoSuchStrategyException e) {
            e.printStackTrace();
            throw new CardActivationException();
        }
    }

}
