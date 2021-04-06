package it.polimi.ingsw.model.leaderCards;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Dashboard;

/**
 * LeaderCard activation requirement regarding development cards owned by a player
 */
public class DevelopmentRequirement implements Requirement{
    private int numberCards;
    private int levelCard;
    private Color color;

    public DevelopmentRequirement(int numberCards, int levelCard, Color color){
        this.numberCards = numberCards;
        this.levelCard = levelCard;
        this.color = color;
    }

    @Override
    public boolean isSatisfied(Dashboard dashboard) {
        return false;
    }
}
