package it.polimi.ingsw.model;

import it.polimi.ingsw.model.benefit.Faith;
import it.polimi.ingsw.model.benefit.Resource;
import it.polimi.ingsw.model.exceptions.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;

public class Dashboard {
    //Array of 3 stacks
    private ArrayList<Stack<DevelopmentCard>> developmentDecks;
    private Strongbox strongbox;
    private WarehouseDepot warehouseDepot;
    private ArrayList<Discount> discounts;
    private ArrayList<ExtraProduction> extraProductions;

    /**
     *
     * @return an ArrayList of 3 developmentCard Decks
     */
    public ArrayList<Stack<DevelopmentCard>> getDevelopmentDecks() {
        return developmentDecks;
    }

    /**
     * Get the upper developmentCard in each Deck
     * @return an ArrayList of 3 developmentCards
     */
    public ArrayList<DevelopmentCard> getActivableDevCards(){
        return null;
    }

    /**
     *
     * @return extraProductions(max 2)
     */
    public ArrayList<ExtraProduction> getExtraProductions() {
        return extraProductions;
    }

    /**
     * Add a developmentCard in a deck
     * This method automatically check if you have discounts and use them
     * @param cardToAdd
     * @param deckPosition: between 1 and 3
     * @throws WrongLevelException level of the card on top of the selected deck isn't 1 below the level of cardToAdd
     * @throws InvalidDeckPositionException if deckPosition isn't an integer between 1 and 3
     */
    public void addDevelopmentCard(DevelopmentCard cardToAdd, int deckPosition) throws WrongLevelException, InvalidDeckPositionException {

    }

    /**
     * Add the extra production provided by a leaderCard effect
     * @param extraProd
     */
    public void addExtraProduction(ExtraProduction extraProd) {

    }

    /**
     * Add the discount provided by a leaderCard effect
     * @param discount
     */
    public void addDiscount(Discount discount) {

    }

    /**
     * Activate the production effect of the upper card in the selected deck
     * @param deckPosition: between 1 and 3
     * @throws InvalidDeckPositionException if deckPosition isn't an integer between 1 and 3
     * @return faith points quantity
     */
    public int activateProduction(int deckPosition) throws InvalidDeckPositionException {
        return 0;
    }

    /**
     * Activate the extra production of the selected card
     * @param extraPosition: between 1 and 2
     * @throws InvalidExtraPositionException if extraPosition isn't an integer between 1 and 2
     * @return faith points quantity
     */
    public int activateExtraProduction(int extraPosition) throws InvalidExtraPositionException {
        return 0;
    }

    /**
     * Check the leaderCards Development requirements
     * @param cardsNumber quantity of cards required
     * @param cardsLevel: from 1 to 3
     * @param color
     * @return true if requirement is satisfied
     * @throws WrongLevelException if cardsLevel isn't between 1 and 3
     */
    public boolean checkDevRequirement(int cardsNumber, int cardsLevel, Color color) throws WrongLevelException{
        return true;
    }

    /**
     * Check the leaderCards Resource requirements
     * @param resourceNumber quantity of resources required
     * @param resource
     * @return true if requirement is satisfied
     */
    public boolean checkResRequirement(int resourceNumber, Resource resource) {
        return true;
    }

    /**
     * Produce a resource of your choice sacrificing two other resources
     * @param firstResourceIn
     * @param secondResourceIn
     * @param resourceOut
     * @throws NotEnoughResourcesException if the player doesn't own one or both the resources requested in input
     */
    public void baseProduction(Resource firstResourceIn, Resource secondResourceIn, Resource resourceOut) throws NotEnoughResourcesException{

    }

    private boolean checkResources(Map<Resource,Integer> resources){
        return true;
    }

    private void removeResources(Map<Resource,Integer> resources) throws NotEnoughResourcesException{

    }

}
