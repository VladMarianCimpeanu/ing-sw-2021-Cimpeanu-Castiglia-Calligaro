package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.JsonToLeaderCard;
import it.polimi.ingsw.server.model.exceptions.InvalidReadException;
import it.polimi.ingsw.server.model.exceptions.NoSuchPlayerException;
import it.polimi.ingsw.server.model.leaderCards.LeaderCard;
import it.polimi.ingsw.server.model.market.Market;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This class gives access to all the global objects of the current game: list of players, market, faithPath and all the development cards that have not been bought yet.
 */
public abstract class Game {
    private final ArrayList<Player> players;
    private Market market;
    private final DevelopmentCardSet developmentCardSet;
    protected FaithPath faithPath;

    /**
     * This method initializes a new game played by players having the specified identities passes as parameter.
     * @param identities an ArrayList of Identities: each Identity represent a player that will play this game
     * @throws IOException if the files containing all the information about leader cards and development cards can not
     * be found or other generic problems related to these file occurred.
     * @throws InvalidReadException if unknown/unexpected values are encountered during the reading process.
     * @throws NoSuchPlayerException if no player's identity is specified.
     */
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
     * @return the list of players playing the current game.
     */
    public ArrayList<Player> getPlayers(){
        return new ArrayList<>(players);
    }
    /**
     * @return true whether the game is ended or false if it is not.
     */
    public abstract boolean isGameEnded();

    /**
     * @return the market of the current game.
     */
    public Market getMarket(){
        return market;
    }

    /**
     * @return a DevelopmentCardSet which contains all the development cards have not been bought yet.
     */
    public DevelopmentCardSet getDevelopmentCardSet() {
        return developmentCardSet;
    }

    /**
     * @return faithPath of the current game.
     */
    public FaithPath getFaithPath() {
        return faithPath;
    }
}
