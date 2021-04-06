package it.polimi.ingsw.model;

import it.polimi.ingsw.model.leaderCards.LeaderCard;
import it.polimi.ingsw.model.market.Market;

import java.util.ArrayList;
import java.util.Stack;

public abstract class Game {
    private ArrayList<Player> players;
    private Market market;
    private DevelopmentCardSet developmentCardSet;
    protected FaithPath faithPath;  //protected? since the different instantiation in multiplayer and single player

    public Game(ArrayList<Identity> identities){

    }
    /**
     * assign leader cards to the players inside the game
     * (called before the first turn)
     */
    private void distributeLeaderCards(){ }

    /**
     * @return the list of players playing the current game
     */
    public ArrayList<Player> getPlayers(){
        return players;
    }
    /**
     * @return true whether the game is ended or false if it is not
     */
    public abstract boolean isGameEnded();

    public Market getMarket(){
        return market;
    }

    public DevelopmentCardSet getDevelopmentCardSet() {
        return developmentCardSet;
    }

    public FaithPath getFaithPath() {
        return faithPath;
    }
}
