package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.NoSuchPlayerException;
import it.polimi.ingsw.model.leaderCards.LeaderCard;
import it.polimi.ingsw.model.market.Market;

import java.util.ArrayList;
import java.util.Stack;

public abstract class Game {
    private ArrayList<Player> players;
    private Market market;
    private DevelopmentCardSet developmentCardSet;
    protected FaithPath faithPath;

    public Game(ArrayList<Identity> identities){
        //Reading LeaderCards somehow
        //Reading DevelopmentCards to create developmentCardSet
        ArrayList<LeaderCard> lc = new ArrayList<>();
        players = new ArrayList<>();
        for(int i = 0; i<4; i++) {
            ArrayList<LeaderCard> lc_p = new ArrayList<>();
            for(int j = 0; j<4; j++)
                lc_p.add(lc.get(4*i+j));
            players.add(new Player(identities.get(i), this, lc_p, new Dashboard(new Strongbox(), new WarehouseDepot())));
        }
        market = new Market();
    }
    /**
     * assign leadercards to the players inside the game
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
