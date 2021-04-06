package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.NoCardException;
import it.polimi.ingsw.model.exceptions.RequirementException;
import it.polimi.ingsw.model.leaderCards.LeaderCard;

import java.util.ArrayList;

public class Player {
    private Identity identity;
    private Dashboard dashboard;
    private ArrayList<LeaderCard> leaderCards;
    private int victoryPoints;
    private ArrayList<MarketStrategy> marketStrategies;
    private Game game;

    public Player(Identity identity, int victoryPoints, int faithPoints){ }

    /**
     *turns the state of a LeaderCard(already owned by the player) to active
     * @param leaderCard
     */
    public void activateLeaderCard(LeaderCard leaderCard){
    }


    public ArrayList<LeaderCard> getLeaderCards(){
        return null;
    }

    public Dashboard getDashboard() {
        return dashboard;
    }

    /**
     * @return nickname of the Player through Identity
     */
    public String getNickName(){return null;}
    /**
     * turn the state of a Leader Card(already owned by the player) to rejected
     * @param leaderCard
     */
    public void discardLeaderCard(LeaderCard leaderCard){

    }

    /**
     *
     * @param directionSelection
     * @param position
     * @return
     */
    public int goToMarket(String directionSelection, int position){return 3;}

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


    public void addMarketStrategy(MarketStrategy marketStrategy){}

    /**
     * Fetch DevelopmentCardSet from Game, draw the required card, check if the player owns the requirements needed to buy the card
     * @param color
     * @param level
     * @throws NoCardException if the set of cards required is empty
     * @throws RequirementException if the resources owned by player do not satisfy the ones required to buy the card
     */
    public void buyDevelopmentCard(Color color, int level) throws NoCardException, RequirementException {}    //color <<enum>>

    /**
     *
     */
    public void passStrategiesToMarket(){}


}
