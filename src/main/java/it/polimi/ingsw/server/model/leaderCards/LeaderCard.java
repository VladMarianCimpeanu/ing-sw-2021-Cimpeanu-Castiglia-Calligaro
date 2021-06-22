package it.polimi.ingsw.server.model.leaderCards;

import it.polimi.ingsw.server.model.Dashboard;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.benefit.Resource;
import it.polimi.ingsw.server.model.exceptions.CardActivationException;

import java.util.ArrayList;

/**
 * A leader card has an array of requirements, each of them can be a development requirement or resource one.
 * It also has the amount of victory points (received when a card is activated).
 */
public abstract class LeaderCard {
    private Resource resource;
    private ArrayList<Requirement> requirements;
    private int victoryPointsAmount;
    private int ID;

    public LeaderCard(ArrayList<Requirement> requirements, Resource resource, int victoryPointsAmount, int ID){
        this.requirements = requirements;
        this.resource = resource;
        this.victoryPointsAmount = victoryPointsAmount;
        this.ID = ID;
    }

    public int getID() {
        return ID;
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

    public Resource getResource() {
        return resource;
    }

    public int getVictoryPointsAmount() {
        return victoryPointsAmount;
    }
}
