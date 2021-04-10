package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.InvalidReadException;
import it.polimi.ingsw.model.exceptions.NoSuchPlayerException;
import it.polimi.ingsw.model.leaderCards.LeaderCard;
import it.polimi.ingsw.model.market.Market;

import java.io.IOException;
import java.io.NotSerializableException;
import java.util.ArrayList;
import java.util.Stack;

public abstract class Game {
    private ArrayList<Player> players;
    private Market market;
    private DevelopmentCardSet developmentCardSet;
    protected FaithPath faithPath;

    public Game(ArrayList<Identity> identities) throws IOException, InvalidReadException, NoSuchPlayerException {
        //Reading LeaderCards somehow
        developmentCardSet = new DevelopmentCardSet();
        ArrayList<LeaderCard> leaderCards = new ArrayList<>();
        players = new ArrayList<>();
        market = new Market();
        if(identities == null) throw new NullPointerException();
        if(identities.size() == 0) throw new NoSuchPlayerException();
        for(int i = 0; i < identities.size() ; i++) {
            ArrayList<LeaderCard> leaderCardsPlayer = new ArrayList<>();
            for(int j = 0; j<4; j++)
                //leaderCardsPlayer.add(leaderCards.get(4*i+j));
            players.add(new Player(identities.get(i), this, leaderCardsPlayer, new Dashboard(new Strongbox(), new WarehouseDepot())));
        }
        market = new Market();
    }
    /**
     * assign leaderCards to the players inside the game
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
