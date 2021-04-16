package it.polimi.ingsw.model.leaderCards;

import it.polimi.ingsw.model.Dashboard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.benefit.Resource;
import it.polimi.ingsw.model.exceptions.CardActivationException;

import java.util.ArrayList;

public abstract class LeaderCard {
    private Resource resource;
    private ArrayList<Requirement> requirements;
    private int victoryPointsAmount;

    public LeaderCard(ArrayList<Requirement> requirements, Resource resource, int victoryPointsAmount){
        this.requirements = requirements;
        this.resource = resource;
        this.victoryPointsAmount = victoryPointsAmount;
    }

    /**
     *  turn the state of the leadercard into active
     *  its action depends on the type of skill the card is representing
     */
    public abstract void activeCard(Player player) throws CardActivationException;


    /**
     * @param dashboard
     * @return true whether the requirements to buy the card are satisfied
     */
    public boolean isSatisfied(Dashboard dashboard){
        if (dashboard == null) return false;
        if (requirements == null) return true;
        for(Requirement r: requirements){
            if(! r.isSatisfied(dashboard)) return false;
        }
        return true;
    }

    protected Resource getResource() {
        return resource;
    }

    public int getVictoryPointsAmount() {
        return victoryPointsAmount;
    }
}
