package it.polimi.ingsw.model;

import it.polimi.ingsw.model.actionToken.ActionToken;
import it.polimi.ingsw.model.actionToken.DiscardToken;
import it.polimi.ingsw.model.actionToken.FaithForwardToken;
import it.polimi.ingsw.model.actionToken.ShuffleToken;

import java.util.ArrayList;

public abstract class ActionTokenDeck {
    private static ArrayList<ActionToken> tokens = null;
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
