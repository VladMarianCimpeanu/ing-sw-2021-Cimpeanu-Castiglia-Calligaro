package it.polimi.ingsw.server.model.leaderCards;

import it.polimi.ingsw.server.model.Color;
import it.polimi.ingsw.server.model.Dashboard;
import it.polimi.ingsw.server.model.exceptions.WrongLevelException;

/**
 * LeaderCard activation requirement regarding development cards owned by a player
 * Number of cards of a certain color and level
 */
public class DevelopmentRequirement implements Requirement{
    private int cardsNumber;
    private int cardsLevel;
    private Color color;

    public DevelopmentRequirement(int cardsNumber, int cardsLevel, Color color){
        this.cardsNumber = cardsNumber;
        this.cardsLevel = cardsLevel;
        this.color = color;
    }

    @Override
    public boolean isSatisfied(Dashboard dashboard) {
        if(dashboard == null) return false;
        try {
            return dashboard.checkDevRequirement(cardsNumber, cardsLevel, color);
        } catch (WrongLevelException e) {
            e.printStackTrace();
        }
        return false;
    }
}
