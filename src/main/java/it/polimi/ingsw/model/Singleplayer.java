package it.polimi.ingsw.model;

import it.polimi.ingsw.model.actionToken.ActionToken;
import it.polimi.ingsw.model.exceptions.InvalidReadException;
import it.polimi.ingsw.model.exceptions.NoSuchPlayerException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;

import static java.util.Collections.shuffle;

public class Singleplayer extends Game{
    private Stack<ActionToken> availableActionTokens;

    public Singleplayer(ArrayList<Identity> identities) throws IOException, InvalidReadException, NoSuchPlayerException {
        super(identities);
        ArrayList<ActionToken> tempTokens = ActionTokenDeck.getTokens();
        availableActionTokens = new Stack<>();
        for(ActionToken token : tempTokens) availableActionTokens.push(token);
        shuffle(availableActionTokens);
        faithPath = new SingleFaithPath(getPlayers().get(0));
    }

    public ArrayList<ActionToken> getAvailableActionTokens() {
        return new ArrayList<>(availableActionTokens);
    }

    @Override
    public boolean isGameEnded() {
        return false;
    }

    /**
     *  assign one ActionToken to the player at every turn
     */
    public void drawToken() throws NoSuchPlayerException {    //any exception?
        ActionToken tempToken = availableActionTokens.pop();
        availableActionTokens.add(0, tempToken);
        tempToken.triggerEffect(this);
    }

    /**
     * shuffle the ActionToken Set (available and discarded tokens together)
     */
    public void shuffleToken(){
        shuffle(availableActionTokens);
    }

}
