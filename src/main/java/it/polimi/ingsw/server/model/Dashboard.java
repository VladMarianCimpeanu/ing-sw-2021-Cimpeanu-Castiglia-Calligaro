package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.benefit.Benefit;
import it.polimi.ingsw.server.model.benefit.Faith;
import it.polimi.ingsw.server.model.benefit.Resource;
import it.polimi.ingsw.server.model.exceptions.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;


/**
 * Dashboard is formed by a warehouse depot, strongbox and a set of development cards owned by the player.
 * Stored development cards must respect the following rules:
 * -1 they can be stored up to 3 decks, they will be indexed as 1[left], 2[center], 3[right]
 * -2 at the game's begin decks are empty
 * -3 the first card that can be pushed to a deck must be of level 1
 * -4 card can be added only at the top of a deck
 * -5 a purchased development card can be placed in a deck only if the card at the top has level == purchased card level -1
 * -6 covered cards can not be used
 * -7 development cards can not be discarded from the dashboard.
 * Different types of production can be triggered from dashboard: development card production, base production and extra production.
 * When a production is selected Dashboard will check if the production is affordable. Once the production is selected and approved by the dashboard,
 * the player can start to choose how to pay each resource needed or change production: after a resource is chosen, there will not be any possibility
 * to revert this operation. The produced resources will be effectively available after the production phase is concluded.
 *
 */
public class Dashboard {
    //Array of 3 stacks
    private final ArrayList<Stack<DevelopmentCard>> developmentDecks;
    private final Strongbox strongbox;
    private final WarehouseDepot warehouseDepot;
    private final ArrayList<ExtraProduction> extraProductions;
    //This stack is used to store resources that have to be used
    private ArrayList<Resource> resourcesToPay;
    private Map<Benefit, Integer> benefitsToProduce;
    private boolean isProducing;
    private final Stack<Production> productionsActivated;

    public Dashboard(Strongbox strongbox, WarehouseDepot warehouseDepot){
        this.strongbox = strongbox;
        this.warehouseDepot = warehouseDepot;
        extraProductions = new ArrayList<>();
        developmentDecks = new ArrayList<>();
        resourcesToPay = new ArrayList<>();
        benefitsToProduce = new HashMap<>();
        productionsActivated = new Stack<>();
        //Creates 3 stack of DevelopmentCard
        for(int i = 0; i < 3; i++){
            Stack<DevelopmentCard> deck = new Stack<>();
            developmentDecks.add(deck);
        }
        isProducing = false;
    }

    /**
     * @param id Identifier of the leadercard associated with the extraProduction
     * @return the ExtraProduction selected by id
     * @throws InvalidIDExcpetion if ID is not found in the extraProductions
     */
    public ExtraProduction getExtraProduction(int id) throws InvalidIDExcpetion{
        for(ExtraProduction e: extraProductions){
            if (e.getID() == id) return e;
        }
        throw new InvalidIDExcpetion();
    }

    /**
     * This method return development decks owned by the player: left deck[0], central deck[1],  right deck[2].
     * @return an ArrayList of 3 developmentCard Decks
     */
    public ArrayList<Stack<DevelopmentCard>> getDevelopmentDecks() {
        ArrayList<Stack<DevelopmentCard>> copyDecks = new ArrayList<>();
        for(Stack<DevelopmentCard> deck : developmentDecks){
            Stack<DevelopmentCard> tempStack = new Stack<>();
            for(int index = 0;index < deck.size(); index ++){
                tempStack.push(deck.elementAt(index));
            }
            copyDecks.add(tempStack);
        }
        return copyDecks;

    }

    /**
     * Get the upper developmentCard of each Deck
     * @return an ArrayList of 3 (or less) developmentCards
     */
    public ArrayList<DevelopmentCard> getActivableDevCards(){
        ArrayList<DevelopmentCard> cardsOnTop = new ArrayList<>();
        for(Stack<DevelopmentCard> developmentDeck : developmentDecks){
            if(developmentDeck.size() > 0) cardsOnTop.add(developmentDeck.lastElement());
        }
        return cardsOnTop;
    }

    /**
     * return the card on top of the selected deck
     * @param deckIndex selected deck: values from 1
     * @return card selected
     * @throws NoCardException if the deck is empty
     */
    public DevelopmentCard getCardOnTop(int deckIndex) throws NoCardException, InvalidDeckPositionException {
        if (deckIndex < 1 || deckIndex > 3) throw new InvalidDeckPositionException();
        Stack<DevelopmentCard> selectedDeck = developmentDecks.get(deckIndex - 1);
        if (selectedDeck.size() > 0) return selectedDeck.lastElement();
        else throw new NoCardException();
    }

    /**
     * this method checks if a development card with the specified level can be placed in the dashboard.
     * @param level specified level of the development card
     * @return true if the card is placeable, else false
     */
    public boolean isDevCardPlaceable(int level) throws WrongLevelException {
        if(level < 1 || level > 3) throw new WrongLevelException();
        for(Stack<DevelopmentCard> devDeck : developmentDecks) {
            if ((devDeck.isEmpty() && level == 1 )||(!devDeck.isEmpty() && devDeck.lastElement().getLevel() == level - 1)) return true;
        }
        return false;
    }

    /**
     * This method returns the strongbox associated to this dashboard.
     * @return a StrongBox Object
     */
    public Strongbox getStrongbox() {
        return strongbox;
    }

    /**
     * This method returns all the extra productions available from this dashboard.
     * @return ArrayList of extraProductions
     */
    public ArrayList<ExtraProduction> getExtraProductions() {
        return new ArrayList<>(extraProductions);
    }

    /**
     * Add the specified card to one of the dashboard's decks specified. The card must be placed over a card that is 1 level lower.
     * @param cardToAdd specified card that has to be placed
     * @param deckPosition: between 1 and 3
     * @throws WrongLevelException level of the card on top of the selected deck isn't 1 below the level of cardToAdd
     * @throws InvalidDeckPositionException if deckPosition isn't an integer between 1 and 3
     */
    public void addDevelopmentCard(DevelopmentCard cardToAdd, int deckPosition) throws InvalidDeckPositionException, WrongLevelException {
        if (deckPosition < 1 || deckPosition > 3) throw new InvalidDeckPositionException();
        else if (cardToAdd == null) throw new NullPointerException();
        else if ((developmentDecks.get(deckPosition - 1).isEmpty() && cardToAdd.getLevel() != 1 ) || (!developmentDecks.get(deckPosition - 1).isEmpty() && developmentDecks.get(deckPosition - 1).lastElement().getLevel() != cardToAdd.getLevel() - 1))
            throw new WrongLevelException();
        else {
            developmentDecks.get(deckPosition - 1).push(cardToAdd);
        }
    }

    /**
     * Add the extra production provided by a leaderCard effect
     * @param extraProd specified extra production that is added to dashboard.
     */
    public void addExtraProduction(ExtraProduction extraProd) {
        if (extraProd == null) throw new NullPointerException();
        else extraProductions.add(extraProd);
    }

    /**
     * this method selects the extra Production way of producing benefits: this method do not produce anything, it verifies
     * that this production can be actually be performed, and then memorize this choice.
     * @param extraProduction is the index position in which the extra production that has to perform is stored in extraProductionList .
     * @param resourceOut is the resource that is supposed to be produced at the end of the production.
     * @throws NotEnoughResourcesException if the dashboard can not find resources needed for the production.
     * @throws ProductionStartedException if a resource has already been spent for another production selected before, and it has not finished yet.
     * @throws ProductionUsedException if the extra production has already been used.
     */
    public void selectExtraProduction(int extraProduction, Resource resourceOut) throws NotEnoughResourcesException, ProductionStartedException, ProductionUsedException {
        if(isProducing) throw new ProductionStartedException();
        if(extraProduction < 0 || extraProduction >= extraProductions.size()) throw new IndexOutOfBoundsException();
        if(resourceOut == null) throw new NullPointerException();
        if(productionsActivated.contains(extraProductions.get(extraProduction))) throw new ProductionUsedException();
        Map<Resource, Integer> resourceCost = new HashMap<>();
        resourceCost.put(extraProductions.get(extraProduction).getResourceIn(), 1);
        if(checkResources(resourceCost)){
            if(!resourcesToPay.isEmpty()) productionsActivated.pop();
            productionsActivated.push(extraProductions.get(extraProduction));
            resourcesToPay = new ArrayList<>();
            benefitsToProduce.clear();
            resourcesToPay.add(extraProductions.get(extraProduction).getResourceIn());
            benefitsToProduce.put(resourceOut, 1);
            benefitsToProduce.put(Faith.giveFaith(), 1);
        }
        else throw new NotEnoughResourcesException();
    }

    /**
     * this method selects the base Production way of producing benefits: this method do not produce anything, it verifies
     * that this production can be actually be performed, and then memorize this choice.
     * @param resourceCost are the resources chosen to be spent during the production.
     * @param resourceOut is the resource that is supposed to be produced at the end of the production.
     * @throws NotEnoughResourcesException if the dashboard can not find resources needed for the production.
     * @throws ResourceCostException if the Map of resources is not composed by 2 resources.
     * @throws ProductionStartedException if a resource has already been spent for another production selected before, and it has not finished yet.
     * @throws ProductionUsedException if base production has already been used.
     */
    public void selectBaseProduction(Map<Resource, Integer> resourceCost, Resource resourceOut) throws NotEnoughResourcesException, ResourceCostException, ProductionStartedException, ProductionUsedException {
        if(isProducing) throw new ProductionStartedException();
        if(resourceOut == null || resourceCost == null) throw new NullPointerException();
        if (resourceCost.size() == 2){
            for(Resource res : Resource.values()){
                if(resourceCost.containsKey(res) && resourceCost.get(res) != 1) throw new ResourceCostException();
            }
        }
        if (resourceCost.size() > 2 || resourceCost.size() == 0) throw new ResourceCostException();
        if (resourceCost.size() == 1 && !resourceCost.containsValue(2)) throw new ResourceCostException();
        if (productionsActivated.contains(BaseProduction.getBaseProduction())) throw new ProductionUsedException();
        if (checkResources(resourceCost)) {
            if(!resourcesToPay.isEmpty()) productionsActivated.pop();
            productionsActivated.push(BaseProduction.getBaseProduction());
            resourcesToPay = new ArrayList<>();
            benefitsToProduce.clear();
            for (Resource resource : Resource.values()) {
                if (resourceCost.containsKey(resource)) {
                    for (int index = 0; index < resourceCost.get(resource); index++) resourcesToPay.add(resource);
                }
            }
            benefitsToProduce.put(resourceOut, 1);
        } else throw new NotEnoughResourcesException();
    }

    /**
     * this method selects the development card Production way of producing benefits: this method do not produce anything, it verifies
     * that this production can be actually be performed, and then memorize this choice.
     * @param deckIndex specified index of the card that has to perform the production.
     * @throws InvalidDeckPositionException if the deck does not exist.
     * @throws NotEnoughResourcesException if the dashboard can not find resources needed for the production.
     * @throws NoCardException if the specified deck has no cards at the moment.
     * @throws ProductionStartedException if a resource has already been spent for another production selected before, and it has not finished yet.
     * @throws ProductionUsedException if the selected card has already been used.
     */
    public void selectCardProduction(int deckIndex) throws InvalidDeckPositionException, NotEnoughResourcesException, NoCardException, ProductionStartedException, ProductionUsedException {
        if(isProducing) throw new ProductionStartedException();
        if(deckIndex > 3 || deckIndex < 1) throw new InvalidDeckPositionException();
        if(developmentDecks.get(deckIndex - 1).isEmpty()) throw new NoCardException();
        if(productionsActivated.contains(developmentDecks.get(deckIndex - 1).lastElement())) throw new ProductionUsedException();
        if(checkResources(developmentDecks.get(deckIndex - 1).lastElement().getResourceIn())){
            if(!resourcesToPay.isEmpty()) productionsActivated.pop();
            productionsActivated.push(developmentDecks.get(deckIndex - 1).lastElement());
            resourcesToPay = new ArrayList<>();
            benefitsToProduce.clear();
            for(Resource resource : Resource.values()){
                if(developmentDecks.get(deckIndex - 1).lastElement().getResourceIn().containsKey(resource)){
                    for(int index = 0; index < developmentDecks.get(deckIndex - 1).lastElement().getResourceIn().get(resource); index ++)
                        resourcesToPay.add(resource);
                }
            }
            benefitsToProduce = developmentDecks.get(deckIndex - 1).lastElement().getBenefitsOut();
        }
        else throw new NotEnoughResourcesException();
    }

    /**
     * this method is used to take a specified resource from warehouse depot in order to pay the actual production cost
     * of an already selected production.
     * @param resource specified resource that has to be taken from the warehouse depot.
     * @throws NotEnoughResourcesException if the selected resource can not be found in warehouse depot.
     * @throws RequirementsSatisfiedException if the requirements are already satisfied or there is not any production selected.
     */
    public void takeFromDepot(Resource resource) throws NotEnoughResourcesException, RequirementsSatisfiedException, InvalidResourceException {
        if(resource == null) throw new NullPointerException();
        int resourcesLeft;
        if (resourcesToPay.isEmpty()) throw new RequirementsSatisfiedException();
        if(!resourcesToPay.contains(resource)) throw new InvalidResourceException();
            resourcesLeft = warehouseDepot.removeResource(resource, 1);
            if (resourcesLeft > 0) throw new NotEnoughResourcesException();
            else {
                int index = 0;
                boolean found = false;
                for(; !found ; index ++){
                    if(resourcesToPay.get(index) == resource) {
                        found = true;
                        resourcesToPay.remove(index);
                    }
                }
                isProducing = true;
            }

    }

    /**
     * this method is used to take a specified resource from strongbox in order to pay the actual production cost
     * of an already selected production.
     * @param resource specified resource that has to be taken from the strongbox.
     * @throws NotEnoughResourcesException if the selected resource can not be found in strongbox.
     * @throws RequirementsSatisfiedException if the requirements are already satisfied or there is not any production selected.
     */
    public void takeFromStrongbox(Resource resource) throws NotEnoughResourcesException, RequirementsSatisfiedException, InvalidResourceException {
        if(resource == null) throw new NullPointerException();
        if (resourcesToPay.isEmpty()) throw new RequirementsSatisfiedException();
        if(!resourcesToPay.contains(resource)) throw new InvalidResourceException();
        int resourcesLeft;
        try {
            resourcesLeft = strongbox.removeResource(resource, 1);
            if (resourcesLeft > 0) throw new NotEnoughResourcesException();
            else {
                int index = 0;
                boolean found = false;
                for(; !found ; index ++){
                    if(resourcesToPay.get(index) == resource){
                        found = true;
                        resourcesToPay.remove(index);
                    }
                }
                isProducing = true;
            }
        } catch (NegativeQuantityException e) {
            e.printStackTrace();
        }
    }

    /**
     * this method is used to take a specified resource from an extraSlot in order to pay the actual production cost
     * of an already selected production.
     * @param resource specified resource that has to be taken from an extraSlot.
     * @throws NotEnoughResourcesException if the selected resource can not be found in any extraSlot.
     * @throws RequirementsSatisfiedException if the requirements are already satisfied or there is not any production selected.
     */
    public void takeFromExtraSlot(Resource resource) throws NotEnoughResourcesException, RequirementsSatisfiedException, InvalidResourceException {
        if(resource == null) throw new NullPointerException();
        if (resourcesToPay.isEmpty()) throw new RequirementsSatisfiedException();
        if(!resourcesToPay.contains(resource)) throw new InvalidResourceException();
        int index = 0;
        boolean found = false;
        ArrayList<ExtraSlot> slotList = warehouseDepot.getExtraSlotList();
        //fixed
        if(slotList.isEmpty()) throw new NotEnoughResourcesException();
        //why take always the first slot of the list?
        int resourcesLeft = slotList.get(index).removeResource(1);
        if (resourcesLeft > 0) throw new NotEnoughResourcesException();
        for(; !found ; index ++){
            if(resourcesToPay.get(index) == resource){
                found = true;
                resourcesToPay.remove(index);
            }
        }
        isProducing = true;
    }

    /**
     * Activate the production selected in this round.
     * @return number of faithPoints earned during the production.
     * @throws NoProductionAvailableException if a production has not selected yet or if not all the resources to pay have been already selected.
     */
    public int activateProduction() throws NoProductionAvailableException {
        if(benefitsToProduce.isEmpty() || !resourcesToPay.isEmpty() ) throw new NoProductionAvailableException();
        for(Resource resource : Resource.values())
            if (benefitsToProduce.containsKey(resource)) {
                try {
                    strongbox.addResource(resource, benefitsToProduce.get(resource));
                } catch (NegativeQuantityException e) {
                    e.printStackTrace();
                }
            }
        isProducing = false;
        return benefitsToProduce.getOrDefault(Faith.giveFaith(), 0);
    }

    /**
     * Check leaderCards Development requirements
     * @param cardsNumber quantity of cards required
     * @param cardsLevel: from 0 to 3: if level is 0 it means that cardsLevel actually is not a requirement.
     * @param color development card color that has to be searched
     * @return true if requirement is satisfied
     * @throws WrongLevelException if cardsLevel isn't between 0 and 3
     */
    public boolean checkDevRequirement(int cardsNumber, int cardsLevel, Color color) throws WrongLevelException{
        if(cardsLevel > 3 || cardsLevel < 0) throw new WrongLevelException();
        for(Stack<DevelopmentCard> deck : developmentDecks){
            for(DevelopmentCard devCard : deck) {
                if((cardsLevel == 0 || cardsLevel == devCard.getLevel()) && devCard.getColor() == color) {
                    cardsNumber -= 1;
                    if (cardsNumber == 0) return true;
                }
            }
        }
        return false;
    }

    /**
     * Check if the indicated resource is available: resources which will be considered during the research come from
     * strongbox, warehouse depot and eventually extra slots
     * @param resourceNumber quantity of resources required
     * @param resource specific resource that has to be searched
     * @return true if requirement is satisfied, else false.
     */
    public boolean checkResRequirement(int resourceNumber, Resource resource) {
        if(resourceNumber <= 0) return true;
        Map<Resource, Integer> requirement = new HashMap<>();
        requirement.put(resource, resourceNumber);
        return checkResources(requirement);
    }

    /**
     * this method checks if the owner of this dashboard can afford to spend the specified resources.
     * @param resources specified resources in a Map where Resource is a key and the value is an Integer indicating quantity of the Resource.
     * @return true if all the resources can be spent, else false.
     */
    public boolean checkResources(Map<Resource,Integer> resources) {
        if(resources == null) throw new NullPointerException();
        ArrayList<ExtraSlot> tempExtraSlots = warehouseDepot.getExtraSlotList();
        for(Resource resource : Resource.values()) {
            if (resources.containsKey(resource)) {
                int tempQuantity = resources.get(resource);
                if (tempQuantity > 0) {
                    tempQuantity -= strongbox.getResourceQuantity(resource);
                    if (tempQuantity > 0) {
                        tempQuantity -= warehouseDepot.getResourceQuantity(resource);
                        if (tempQuantity > 0) {
                            for (ExtraSlot slot : tempExtraSlots) {
                                if (slot.getResource() == resource) tempQuantity -= slot.getQuantity();
                            }
                        }
                    }
                }
                if (tempQuantity > 0) return false;
            }
        }
        return true;
    }

    /**
     * This method returns the selected dashboard's warehouse depot.
     * @return the selected dashboard's warehouse depot.
     */
    public WarehouseDepot getWarehouseDepot() {
        return warehouseDepot;
    }

    /**
     * This method lists all the resources that have not been paid yet in order to activate a production.
     * @return ArrayList of resources that has to be paid: the same resource can appears multiple times in this ArrayList.
     */
    public ArrayList<Resource> getResourcesToPay() {
        return new ArrayList<>(resourcesToPay);
    }

    /**
     * This method lists all the benefits that have to be produced due to a production activation.
     * @return a Map containing Benefits to be produced as Key and Integers as values indicating quantity to be produced.
     */
    public Map<Benefit, Integer> getBenefitsToProduce() {
        return new HashMap<>(benefitsToProduce);
    }

    /**
     * This method makes the dashboard forget about productions used.
     */
    public void refreshProductions(){
        productionsActivated.clear();
    }

    /**
     * it clears all the resources that must be paid, benefits to be produced and all the productions activated.
     */
    public void refreshState() {
        resourcesToPay.clear();
        benefitsToProduce.clear();
        isProducing = false;
        productionsActivated.clear();
    }

    /**
     * it pays all the resources needed for the selected production.
     */
    public void automatizePayment() {
        //fixed bug with only 2 resources in strongbox
        ArrayList<Resource> resources = resourcesToPay;
        for(Resource resource : resources) {
            try {
                takeFromDepot(resource);
            } catch (NotEnoughResourcesException e) { // if resource is not in depot, checks if resource is stored in an extraSlot
                try {
                    takeFromExtraSlot(resource);
                } catch (NotEnoughResourcesException notEnoughResourcesException) { // if resource is not in an extra slot checks in strongbox.
                    try {
                        takeFromStrongbox(resource);
                    } catch (NotEnoughResourcesException | InvalidResourceException | RequirementsSatisfiedException enoughResourcesException) {
                        enoughResourcesException.printStackTrace();
                    }
                } catch (RequirementsSatisfiedException | InvalidResourceException requirementsSatisfiedException) {
                    requirementsSatisfiedException.printStackTrace();
                }
            } catch (RequirementsSatisfiedException | InvalidResourceException e) {
                e.printStackTrace();
            }
        }
        resourcesToPay.clear();
    }
}
