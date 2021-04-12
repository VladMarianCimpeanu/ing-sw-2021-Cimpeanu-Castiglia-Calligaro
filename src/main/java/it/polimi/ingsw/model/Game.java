package it.polimi.ingsw.model;

import it.polimi.ingsw.JsonToLeaderCard;
import it.polimi.ingsw.model.exceptions.InvalidReadException;
import it.polimi.ingsw.model.exceptions.NoSuchPlayerException;
import it.polimi.ingsw.model.leaderCards.LeaderCard;
import it.polimi.ingsw.model.market.Market;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public abstract class Game {
    private ArrayList<Player> players;
    private Market market;
    private DevelopmentCardSet developmentCardSet;
    protected FaithPath faithPath;

    public Game(ArrayList<Identity> identities) throws IOException, InvalidReadException, NoSuchPlayerException {
        // Obtaining a set of leaderCards available for this game:
        ArrayList<LeaderCard> leaderCards = JsonToLeaderCard.readLeaderCards();
        Collections.shuffle(leaderCards);
        // Obtaining a set of developmentCards available for this game
        developmentCardSet = new DevelopmentCardSet();
        players = new ArrayList<>();
        market = new Market();
        if(identities == null) throw new NullPointerException();
        if(identities.size() == 0) throw new NoSuchPlayerException();
        // Distributing leaderCards to players
        for (Identity identity : identities) {
            ArrayList<LeaderCard> leaderCardsPlayer = new ArrayList<>();
            for (int j = 0; j < 4; j++) {
                leaderCardsPlayer.add(leaderCards.get(0));
                leaderCards.remove(0);
            }
            players.add(new Player(identity, this, leaderCardsPlayer, new Dashboard(new Strongbox(), new WarehouseDepot())));
        }
        market = new Market();
    }

    /**
     * @return the list of players playing the current game
     */
    public ArrayList<Player> getPlayers(){
        return new ArrayList<>(players);
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
