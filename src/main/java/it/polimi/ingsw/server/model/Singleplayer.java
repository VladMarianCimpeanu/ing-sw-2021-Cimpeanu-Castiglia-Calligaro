package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.actionToken.ActionToken;
import it.polimi.ingsw.server.model.exceptions.InvalidReadException;
import it.polimi.ingsw.server.model.exceptions.NoSuchPlayerException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

import static java.util.Collections.shuffle;

/**
 * The Singleplayer class implements the singleplayer game mode. The difference between multiplayer and singleplayer is the latter
 * gives access to a deck of action tokens: this objects are used only in single player mode.
 */
public class Singleplayer extends Game{
    private final Stack<ActionToken> availableActionTokens;

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
     * it triggers the effect of the token on top of the deck of action tokens.
     */
    public void drawToken() throws NoSuchPlayerException {    //any exception?
        ActionToken tempToken = availableActionTokens.pop();
        availableActionTokens.add(0, tempToken);
        System.out.println(tempToken);
        virtualView.updateDrawToken(tempToken.getID());
        tempToken.triggerEffect(this);
    }

    /**
     * shuffle the ActionToken Set (available and discarded tokens together)
     */
    public void shuffleToken(){
        shuffle(availableActionTokens);
    }

    @Override
    public void endTurn() throws NoSuchPlayerException{
            drawToken();
    }

}
