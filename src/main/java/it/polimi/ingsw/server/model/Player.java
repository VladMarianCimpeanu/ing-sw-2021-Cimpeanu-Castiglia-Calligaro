package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.controller.VirtualView;
import it.polimi.ingsw.server.model.benefit.Benefit;
import it.polimi.ingsw.server.model.benefit.Faith;
import it.polimi.ingsw.server.model.benefit.Resource;
import it.polimi.ingsw.server.model.exceptions.*;
import it.polimi.ingsw.server.model.leaderCards.LeaderCard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * This class represent the player.
 * A player can perform each action that a real player can perform in the physical game.
 */
public class Player {
    private Identity identity;
    private Dashboard dashboard;
    //leaderCards not yet activated
    private ArrayList<LeaderCard> leaderCards;
    private ArrayList<LeaderCard> activatedLeader;
    private int victoryPoints;
    private ArrayList<MarketStrategy> marketStrategies;
    private ArrayList<Discount> discountList;
    private Game game;
    private Map<Resource,Integer> developmentCardCost;
    private ArrayList<Benefit> receivedFromMarket;
    private DevelopmentCard devCardToAdd;
    private int whiteMarbles;
    //when a player go to market this stack contains chosen strategy for each white marble
    private Stack<MarketStrategy> marketStrategyStack;
    private VirtualView virtualView;

    public Player(Identity identity, Game game, ArrayList<LeaderCard> leaderCards, Dashboard dashboard){
        this.identity = identity;
        this.game = game;
        this.leaderCards = leaderCards;
        activatedLeader = new ArrayList<>();
        victoryPoints = 0;
        this.dashboard = dashboard;
        marketStrategies = new ArrayList<>();
        developmentCardCost = new HashMap<>();
        discountList = new ArrayList<>();
        marketStrategyStack = new Stack<>();
    }

    /**
     * @param id Identifier of the leadercard associated with the discount
     * @return the discount selected by id
     * @throws InvalidIDExcpetion if ID is not found in the discount list
     */
    public Discount getDiscount(int id) throws InvalidIDExcpetion{
        for(Discount d: discountList){
            if (d.getID() == id) return d;
        }
        throw new InvalidIDExcpetion();
    }

    /**
     * @param id Identifier of the leadercard associated with the market strategy
     * @return the market strategy selected by id
     * @throws InvalidIDExcpetion if ID is not found in the market strategy list
     */
    public MarketStrategy getMarketStrategy(int id) throws InvalidIDExcpetion{
        for(MarketStrategy m: marketStrategies){
            if (m.getID() == id) return m;
        }
        throw new InvalidIDExcpetion();
    }

    public void keepLeaderCards(LeaderCard card1, LeaderCard card2) throws NoCardException {
        if(leaderCards.contains(card1) && leaderCards.contains(card2) && !card1.equals(card2)){
            leaderCards = new ArrayList<>();
            leaderCards.add(card1);
            leaderCards.add(card2);
        }else throw new NoCardException();
    }

    /**
     * @return false if there are resources from market to redeem, otherwise return true.
     */
    public boolean isMarketResourcesUnavailable() {
        return receivedFromMarket.isEmpty();
    }
    public ArrayList<LeaderCard> getLeaderCards(){
        return leaderCards;
    }

    public Dashboard getDashboard() {
        return dashboard;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public ArrayList<Discount> getDiscountList() {
        return new ArrayList<Discount>(discountList);
    }

    public ArrayList<MarketStrategy> getMarketStrategies() {
        return marketStrategies;
    }

    public Identity getIdentity() {
        return identity;
    }

    /**
     * @return nickname of the Player through Identity
     */
    public String getNickName(){
        return identity.getNickname();
    }

    public boolean isOnline(){
        return identity.isOnline();
    }

    public Map<Resource,Integer> getDevelopmentCardCost() {
        return new HashMap<Resource,Integer>(developmentCardCost);
    }

    public ArrayList<Resource> getReceivedFromMarket() {
        ArrayList<Resource> resources = new ArrayList<>();
        for(Benefit b: receivedFromMarket){
            resources.add( (Resource) b);
        }
        return resources;
    }

    /**
     * Add the discount provided by a leaderCard effect
     * @param discount
     */
    public void addDiscount(Discount discount) {
        if(discount != null){
            discountList.add(discount);
        }
    }

    /**
     * change number of victory points achieved by the player
     * @param quantity
     */
    public void addVictoryPoints(int quantity){
        if(quantity > 0) {
            victoryPoints += quantity;
        }
    }

    public void addMarketStrategy(MarketStrategy marketStrategy) throws NoSuchStrategyException{
        if(marketStrategy == null) throw new NoSuchStrategyException();
        marketStrategies.add(marketStrategy);
    }

    /**
     * number of steps forward on the FaithPath
     * @param faithPoints
     */
    public void addFaithPoint(int faithPoints){
        if(faithPoints > 0){
            try {
                game.getFaithPath().movePlayer(this, faithPoints);
            } catch (NoSuchPlayerException | InvalidStepsException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * turn the state of a Leader Card(already owned by the player) to rejected
     * @param leaderCard
     */
    public void discardLeaderCard(LeaderCard leaderCard) {
        if(leaderCard != null && leaderCards.remove(leaderCard)){
            virtualView.updateDiscardLeaderCard(leaderCard.getID());
            addFaithPoint(1);
        }
    }

    /**
     *turns the state of a LeaderCard(already owned by the player) to active
     * @param leaderCard
     */
    public void activateLeaderCard(LeaderCard leaderCard) throws RequirementException{
        if(leaderCard != null && leaderCards.contains(leaderCard)){

            if(!leaderCard.isSatisfied(getDashboard())){
                throw new RequirementException();
            }

            try {
                leaderCard.activeCard(this);
                virtualView.updateActiveLeaderCard(leaderCard.getID());
                addVictoryPoints(leaderCard.getVictoryPointsAmount());
                leaderCards.remove(leaderCard);
                activatedLeader.add(leaderCard);
            } catch (CardActivationException e) {
                e.printStackTrace();
                throw new RequirementException();
            }
        }
    }

    /************* BUY A DEVELOPMENT CARD PROCESS **************/
    private void removeOne(Map<Resource,Integer> map, Resource resource){
        int old = map.get(resource);
        if(old > 1){
            map.replace(resource,old - 1);
        }else if(old == 1){
            map.remove(resource);
        }
    }

    /**
     * Fetch DevelopmentCardSet from Game, peek the required card, check if the player owns the requirements needed to buy the card
     * @param color
     * @param level
     * @param discounts null if the player doesn't want to use any discount
     * @throws NoCardException if the set of cards required is empty
     * @throws NotEnoughResourcesException if the resources owned by player do not satisfy the ones required to buy the card
     * @throws WrongLevelException if the card is not placeable in any position of the deck
     */
    public void drawDevelopmentCard(Color color, int level, ArrayList<Discount> discounts) throws NoCardException, InvalidDiscountException , NotEnoughResourcesException, WrongLevelException {
        if(color == null) return;
        DevelopmentCardSet set = game.getDevelopmentCardSet();
        if(set.getAvailableQuantity(color, level) <= 0) throw new NoCardException();

        DevelopmentCard wanted = set.peekCard(color, level);
        Map<Resource,Integer> cost = wanted.getResourceCost();

        if(!dashboard.isDevCardPlaceable(level)) throw new WrongLevelException();

        if(discounts != null && !discounts.isEmpty()){
            for(Discount discount: discounts){
                if(!discountList.contains(discount)){
                    throw new InvalidDiscountException();
                }else{
                    if(cost.containsKey(discount.getResource())){
                        removeOne(cost, discount.getResource());
                    }
                }
            }
        }

        if(!dashboard.checkResources(cost)) throw new NotEnoughResourcesException();

        devCardToAdd = set.drawCard(color, level);
        developmentCardCost = cost;
    }

    /**
     * Pay a resource contained in the developmentCardCost map
     * @param resource to remove from developmentCardCost
     * @throws NotEnoughResourcesException if the strongbox doesn't contain the resource in the selected index
     */
    public void payFromStrongbox(Resource resource) throws NotEnoughResourcesException{
        if(resource == null) return;
        if(dashboard.getStrongbox().getResourceQuantity(resource) <= 0) throw new NotEnoughResourcesException();
        if(developmentCardCost.containsKey(resource)){
            try {
                dashboard.getStrongbox().removeResource(resource,1);
                removeOne(developmentCardCost, resource);
            } catch (NegativeQuantityException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Pay a resource contained in the developmentCardCost map
     * @param resource to remove from developmentCardCost
     * @throws NotEnoughResourcesException if the warehouseDepot doesn't contain the resource in the selected index
     */
    public void payFromWarehouseDepot(Resource resource) throws NotEnoughResourcesException{
        if(resource == null) return;
        if(dashboard.getWarehouseDepot().getResourceQuantity(resource) <= 0) throw new NotEnoughResourcesException();
        if(developmentCardCost.containsKey(resource)){
            dashboard.getWarehouseDepot().removeResource(resource,1);
            removeOne(developmentCardCost, resource);
        }
    }

    /**
     * Pay a resource contained in the developmentCardCost map
     * @param resource to remove from developmentCardCost
     * @throws NotEnoughResourcesException if the extraSlot doesn't contain the resource in the selected index
     */
    public void payFromExtraSlot(Resource resource) throws NotEnoughResourcesException{
        if(resource == null) return;
        if(dashboard.getWarehouseDepot().getExtraQuantity(resource) <= 0) throw new NotEnoughResourcesException();
        if(developmentCardCost.containsKey(resource)){
            try {
                dashboard.getWarehouseDepot().removeExtraResource(resource,1);
                removeOne(developmentCardCost, resource);
            } catch (MissingExtraSlot missingExtraSlot) {
                missingExtraSlot.printStackTrace();
                throw new NotEnoughResourcesException();
            }
        }
    }

    /**
     * Place Development Card in the selected deck
     * @param position between 1 and 3
     * @throws WrongLevelException if the card is not placeable at the given position
     */
    public void placeDevelopmentCard(int position) throws WrongLevelException{
        if(!developmentCardCost.isEmpty()) return;
        if(position < 1 || position > 3) throw new WrongLevelException();
        try {
            dashboard.addDevelopmentCard(devCardToAdd, position);
            virtualView.updateDevDeck(position, devCardToAdd.getID());
        } catch (InvalidDeckPositionException e) {
            e.printStackTrace();
            throw new WrongLevelException();
        }
    }
    /************* BUY A DEVELOPMENT CARD PROCESS **************/



    /***************** GO TO MARKET PROCESS ********************/
    /**
     *
     * @param directionSelection
     * @param position
     * @return the number of White Marbles picked
     * @throws OutOfBoundColumnsException when position < 0 && position > 3
     * @throws OutOfBoundRowException when position < 0 && position > 2
     * @throws InvalidDirectionSelection
     */
    public int goToMarket(String directionSelection, int position) throws OutOfBoundColumnsException, OutOfBoundRowException, InvalidDirectionSelection {
        if(directionSelection == null){
            throw new InvalidDirectionSelection();
        }else if(directionSelection.equals("row") || directionSelection.equals("Row")){
            whiteMarbles = game.getMarket().selRow(position);
        }else if(directionSelection.equals("column") || directionSelection.equals("Column")){
            whiteMarbles =  game.getMarket().selColumn(position);
        }else{
            throw new InvalidDirectionSelection();
        }
        virtualView.updateStrategies(whiteMarbles, 0);
        return whiteMarbles;
    }

    /**
     * Add a strategy to the stack before converting marbles in the market
     * @param marketStrategy the strategy to add
     * @throws InvalidStrategyException if the player doesn't own the strategy or there are no more strategies to use.
     */
    public void addInMarketStrategyStack(MarketStrategy marketStrategy) throws InvalidStrategyException {
        if(marketStrategy == null || !marketStrategies.contains(marketStrategy)) throw new InvalidStrategyException();
        if(whiteMarbles == 0) throw new InvalidStrategyException();
        marketStrategyStack.push(marketStrategy);
        whiteMarbles -= 1;
        virtualView.updateStrategies(whiteMarbles, marketStrategy.getID());
    }

    /**
     * Pass marketStrategyStack to the market and receive converted marbles
     * @throws InvalidStrategyException
     */
    public void passStrategiesToMarket() throws InvalidStrategyException {
        ArrayList<Benefit> received;
        if(marketStrategies.isEmpty()) received = game.getMarket().convertMarbles();
        else received = game.getMarket().convertMarbles(marketStrategyStack);
        receivedFromMarket = new ArrayList<>();
        for(Benefit b: received){
            if(b.equals(Faith.giveFaith())){
                System.out.println(received);
                addFaithPoint(1);
            }else{
                receivedFromMarket.add(b);
            }
        }
        whiteMarbles = 0;
        virtualView.updateConvertedMarbles(receivedFromMarket);
    }

    /**
     * Put a resource in warehouse depot
     */
    public void putInWarehouseDepot(Resource resource, int shelf) throws InvalidResourceException, ExistingResourceException, InvalidShelfPosition, NotEnoughSpaceException {
        if(resource == null) throw new InvalidResourceException();
        if(receivedFromMarket.contains(resource)){
            if (dashboard.getWarehouseDepot().addResource(shelf,1,resource) == 1) throw new NotEnoughSpaceException();
            else receivedFromMarket.remove(resource);
        }
        virtualView.updateConvertedMarbles(receivedFromMarket);
    }

    /**
     * Put a resource in extra slot
     */
    public void putInExtraSlot(Resource resource) throws NotEnoughSpaceException, InvalidResourceException, MissingExtraSlot {
        if(resource == null) throw new InvalidResourceException();
        if(receivedFromMarket.contains(resource)){
            if (dashboard.getWarehouseDepot().addExtraResource(resource,1) == 1) throw new NotEnoughSpaceException();
            else receivedFromMarket.remove(resource);
        }
        virtualView.updateConvertedMarbles(receivedFromMarket);
    }

    /**
     * Discard a resource
     */
    public void discardResource(Resource resource) throws InvalidResourceException {
        if(resource == null) throw new InvalidResourceException();//
        if(receivedFromMarket.contains(resource)){
            receivedFromMarket.remove(resource);
        }
        try {
            game.getFaithPath().moveOpponents(this);
        } catch (NoSuchPlayerException | InvalidStepsException e) {
            e.printStackTrace();
        }
        virtualView.updateConvertedMarbles(receivedFromMarket);
    }
    /***************** GO TO MARKET PROCESS ********************/

    public void subscribe(VirtualView virtualView){
        this.virtualView = virtualView;
    }

    public void completePayment() throws NotEnoughResourcesException{
        for(Resource r: Resource.values()){
            if(developmentCardCost.containsKey(r)){
                int remaining = developmentCardCost.get(r);
                try {
                    if(remaining != 0) remaining = dashboard.getWarehouseDepot().removeExtraResource(r, remaining);
                } catch (MissingExtraSlot missingExtraSlot) {}
                if(remaining != 0) remaining = dashboard.getWarehouseDepot().removeResource(r, remaining);
                try {
                    if(remaining != 0) remaining = dashboard.getStrongbox().removeResource(r, remaining);
                } catch (NegativeQuantityException e) {
                    e.printStackTrace();
                }
                if(remaining != 0) throw new NotEnoughResourcesException();
                developmentCardCost.remove(r);
            }
        }
    }

    public void autoPlace() throws WrongLevelException{
        for(int i = 0; i < 3; i++){
            try {
                placeDevelopmentCard(i);
                return;
            } catch (WrongLevelException e) {}
        }
        throw new WrongLevelException();
    }

    public ArrayList<LeaderCard> getActivatedLeader() {
        return new ArrayList<>(activatedLeader);
    }
}
