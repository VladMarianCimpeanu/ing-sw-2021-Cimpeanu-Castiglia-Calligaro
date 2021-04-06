package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.model.leaderCards.LeaderCard;

import java.util.ArrayList;

public class Player {
    private Identity identity;
    private Dashboard dashboard;
    private ArrayList<LeaderCard> leaderCards;
    private int victoryPoints;
    private ArrayList<MarketStrategy> marketStrategies;
    private Game game;

    public Player(Identity identity, Game game, ArrayList<LeaderCard> leaderCards, Dashboard dashboard){
        this.identity = identity;
        this.game = game;
        this.leaderCards = leaderCards;
        victoryPoints = 0;
        this.dashboard = dashboard;
        marketStrategies = new ArrayList<>();
    }

    public Game getGame() {
        return game;
    }

    /**
     *turns the state of a LeaderCard(already owned by the player) to active
     * @param leaderCard
     */
    public void activateLeaderCard(LeaderCard leaderCard) throws RequirementException{
    }

    public ArrayList<LeaderCard> getLeaderCards(){
        return null;
    }

    public Dashboard getDashboard() {
        return dashboard;
    }

    public int getVictoryPoints() { return victoryPoints; }

    public ArrayList<MarketStrategy> getMarketStrategies() { return marketStrategies; }

    /**
     * @return nickname of the Player through Identity
     */
    public String getNickName(){return null;}
    /**
     * turn the state of a Leader Card(already owned by the player) to rejected
     * @param leadercard
     */
    public void discardLeaderCard(LeaderCard leadercard) throws RequirementException{
    }

    /**
     *
     * @param directionSelection
     * @param position
     * @return the number of White Marbles picked
     * @throws OutOfBoundColumnsException when position < 1 && position > 4
     * @throws OutOfBoundRowException when position < 1 && position > 3
     * @throws InvalidDirectionSelection
     */
    public int goToMarket(String directionSelection, int position)
            throws OutOfBoundColumnsException, OutOfBoundRowException, InvalidDirectionSelection {
        return 3;
    }

    /**
     * change number of victory points achieved by the player
     * @param quantity
     */
    public void addVictoryPoints(int quantity){

    }

    /**
     * number of steps forward on the FaithPath
     * @param faithPoints
     */
    public void addFaithPoint(int faithPoints){

    }


    public void addMarketStrategy(MarketStrategy marketStrategy) throws NoSuchStrategyException{}

    /**
     * Fetch DevelopmentCardSet from Game, peek the required card, check if the player owns the requirements needed to buy the card
     * @param color
     * @param level
     * @throws NoCardException if the set of cards required is empty
     * @throws RequirementException if the resources owned by player do not satisfy the ones required to buy the card
     */
    public void buyDevelopmentCard(Color color, int level) throws NoCardException, RequirementException, WrongLevelException {}    //color <<enum>>

    /**
     *
     */
    public void passStrategiesToMarket(){}


}
