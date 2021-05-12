package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.actionToken.ActionToken;
import it.polimi.ingsw.server.model.actionToken.DiscardToken;
import it.polimi.ingsw.server.model.actionToken.FaithForwardToken;
import it.polimi.ingsw.server.model.actionToken.ShuffleToken;

import java.util.ArrayList;

/**
 * This class is used to distribute a new set of action tokens.
 */
public abstract class ActionTokenDeck {
    private static ArrayList<ActionToken> tokens = null;

    /**
     * @return a non randomized ArrayList of ActionTokens.
     */
    public static ArrayList<ActionToken> getTokens() {
        if (tokens == null){
            tokens = new ArrayList<>();
            for(Color color : Color.values()) tokens.add(new DiscardToken(color));
            tokens.add(new FaithForwardToken());
            tokens.add(new ShuffleToken());
        }
        return new ArrayList<>(tokens);
    }
}
