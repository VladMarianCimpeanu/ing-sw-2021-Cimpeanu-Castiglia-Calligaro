package it.polimi.ingsw.model;

import it.polimi.ingsw.model.benefit.Resource;

import java.nio.file.NoSuchFileException;
import java.util.Map;


public class DevelopmentCard {

    private Map<Resource, Integer> ResourceIn;
    private Map<Resource, Integer> ResourceOut;
    private Map<Resource, Integer> ResourceCost;
    private int level;
    private Color color;
    private int victoryPointsAmount;

    public DevelopmentCard(String filePathCard) throws NoSuchFileException {

    }
    /**
     * get the value of the card in terms of victory points
     * @return number of victory points
     */
    public int getVictoryPoints(){
        return victoryPointsAmount; //TO DO
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
        return null; //TO DO
    }

    /**
     * get information about resources produced by this card
     * @return Map containing resources that this card should produce
     */
    public Map<Resource, Integer> getResourceOut() {
        return null; //TO DO
    }

    /**
     * get information about the resources needed to buy this card
     * @return Map containing resources that this card needs to be purchased
     */
    public Map<Resource, Integer> getResourceCost() {
        return null; //TO DO
    }
}
