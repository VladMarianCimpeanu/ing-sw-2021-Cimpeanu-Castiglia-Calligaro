package it.polimi.ingsw.model;

import it.polimi.ingsw.model.actionToken.ActionToken;

import java.util.ArrayList;
import java.util.Stack;

public class Singleplayer extends Game{
    private ArrayList<ActionToken> availableActionTokens;
    public Singleplayer(ArrayList<Player> players){
        super(players);
    }

    @Override
    public boolean isGameEnded() {
        return false;
    }

    /**
     *  assign one ActionToken to the player at every turn
     */
    public void drawToken(){    //any exception?

    }

    /**
     * shuffle the ActionToken Set (available and discarded tokens together)
     */
    public void shuffleToken(){

    }
}
