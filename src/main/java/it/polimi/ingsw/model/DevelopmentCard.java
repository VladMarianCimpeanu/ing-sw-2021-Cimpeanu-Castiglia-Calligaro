package it.polimi.ingsw.model;

import it.polimi.ingsw.model.benefit.Benefit;
import it.polimi.ingsw.model.benefit.Resource;

import java.util.HashMap;
import java.util.Map;


public class DevelopmentCard {

    private  Map<Resource, Integer> resourceIn;
    private  Map<Benefit, Integer> resourceOut;
    private  Map<Resource, Integer> resourceCost;
    private  int level;
    private  Color color;
    private  int victoryPointsAmount;

    public DevelopmentCard(int victoryPointsAmount, int level, Color color, Map<Resource, Integer> resourceCost, Map<Resource, Integer> resourceIn, Map<Benefit, Integer> resourceOut )  {
        this.victoryPointsAmount = victoryPointsAmount;
        this.level = level;
        this.color = color;
        this.resourceCost = resourceCost;
        this.resourceIn = resourceIn;
        this.resourceOut = resourceOut;
    }

    public DevelopmentCard(String path){

    }
    /**
     * get the value of the card in terms of victory points
     * @return number of victory points
     */
    public int getVictoryPoints(){
        return victoryPointsAmount;
    }

    /**
     * get the card's color
     * @return the card's color
     */
    public Color getColor() {
        return color;
    }

    /**
     * get the card's level
     * @return the card's level
     */
    public int getLevel() {
        return level;
    }

    public Map<Resource, Integer> getResourceIn() {
        return new HashMap<>(resourceIn);
    }

    /**
     * get information about resources and faith points produced by this card
     * @return Map containing resources and faith points that this card should produce
     */
    public Map<Benefit, Integer> getBenefitsOut() {
        return new HashMap<>(resourceOut);
    }

    /**
     * get information about the resources needed to buy this card
     * @return Map containing resources that this card needs to be purchased
     */
    public Map<Resource, Integer> getResourceCost() {
        return new HashMap<>(resourceCost);
    }

}
