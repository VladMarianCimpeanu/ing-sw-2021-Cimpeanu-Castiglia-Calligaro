package it.polimi.ingsw.model.leaderCards;

import it.polimi.ingsw.model.Dashboard;
import it.polimi.ingsw.model.benefit.Resource;

/**
 * LeaderCard activation requirement regarding resources owned by a player
 */

public class ResourceRequirement implements Requirement{
    private int numberResources;
    private Resource resource;

    public ResourceRequirement(int numberResources, Resource resource){
        this.numberResources = numberResources;
        this.resource = resource;
    }

    @Override
    public boolean isSatisfied(Dashboard dashboard) {
        if(dashboard == null) return false;
        return dashboard.checkResRequirement(numberResources, resource);
    }
}
