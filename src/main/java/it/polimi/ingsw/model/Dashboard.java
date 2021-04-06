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

    public Dashboard(Strongbox strongbox, WarehouseDepot warehouseDepot){
        this.strongbox = strongbox;
        this.warehouseDepot = warehouseDepot;
        discounts = new ArrayList<Discount>();
        extraProductions = new ArrayList<ExtraProduction>();
        developmentDecks = new ArrayList<Stack<DevelopmentCard>>();
        //Creates 3 stack of DevelopmentCard
        for(int i = 0; i < 2; i++){
            Stack<DevelopmentCard> deck = new Stack<DevelopmentCard>();
            developmentDecks.add(deck);
        }
    }

    /**
     *
     * @return an ArrayList of 3 developmentCard Decks
     */
    public ArrayList<Stack<DevelopmentCard>> getDevelopmentDecks() {
        return developmentDecks;
    }

    /**
     * Get the upper developmentCard in each Deck
     * @return an ArrayList of 3 (or less) developmentCards
     */
    public ArrayList<DevelopmentCard> getActivableDevCards(){
        return null;
    }


    public Strongbox getStrongbox() {
        return strongbox;
    }


    /**
     *
     * @return extraProductions(max 2)
     */
    public ArrayList<ExtraProduction> getExtraProductions() {
        return extraProductions;
    }

    /**
     * Buy a development card using resources in warehouse depot and strongbox,
     * @param cardToAdd
     * @param deckPosition: between 1 and 3
     * @param discounts array of discounts
     * @throws WrongLevelException level of the card on top of the selected deck isn't 1 below the level of cardToAdd
     * @throws InvalidDeckPositionException if deckPosition isn't an integer between 1 and 3
     * @throws NotEnoughResourcesException if the cost of the card can't be afforded
     */
    public void addDevelopmentCard(DevelopmentCard cardToAdd, int deckPosition, ArrayList<Discount> discounts) throws WrongLevelException, InvalidDeckPositionException, NotEnoughResourcesException {

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
     * @throws InvalidDeckPositionException if deckPosition isn't an integer between 1 and 3 or the position is empty
     * @throws NotEnoughResourcesException if the cost of the card can't be afforded
     * @return faith points quantity
     */
    public int activateProduction(int deckPosition) throws InvalidDeckPositionException, NotEnoughResourcesException {
        return 0;
    }

    /**
     * Activate the extra production of the selected card
     * @param extraPosition: between 1 and 2
     * @throws InvalidExtraPositionException if extraPosition isn't an integer between 1 and 2 or the position is empty
     * @return faith points quantity
     */
    public int activateExtraProduction(int extraPosition, Resource resourceOut) throws InvalidExtraPositionException {
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

    public boolean checkResources(Map<Resource,Integer> resources){
        return true;
    }

    private void removeResources(Map<Resource,Integer> resources) throws NotEnoughResourcesException{

    }

    /**
     *
     * @return
     */
    public ArrayList<Discount> getDiscounts() {
        return discounts;
    }

    /**
     *
     * @return
     */
    public WarehouseDepot getWarehouseDepot() {
        return warehouseDepot;
    }
}
