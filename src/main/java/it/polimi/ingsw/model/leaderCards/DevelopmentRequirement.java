package it.polimi.ingsw.model.leaderCards;

import it.polimi.ingsw.model.Dashboard;

/**
 * LeaderCard activation requirement regarding development cards owned by a player
 */
public class DevelopmentRequirement implements Requirement{
    private int numberCards;
    private int levelCard;
    private String color;

    public DevelopmentRequirement(int numberCards, int levelCard, String color){}

    @Override
    public boolean isSatisfied(Dashboard dashboard) {
        return false;
    }
}
