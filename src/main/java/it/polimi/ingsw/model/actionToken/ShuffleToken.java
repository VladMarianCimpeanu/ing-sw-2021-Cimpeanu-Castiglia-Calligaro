package it.polimi.ingsw.model.actionToken;

import it.polimi.ingsw.model.SingleFaithPath;
import it.polimi.ingsw.model.Singleplayer;
import it.polimi.ingsw.model.exceptions.NoSuchPlayerException;

/**
 * This class represents a specific type of action token.
 */
public class ShuffleToken implements ActionToken{

    private void stepForward(Singleplayer singleplayer) throws NoSuchPlayerException {
        if(singleplayer == null) throw new NoSuchPlayerException();
        SingleFaithPath singleFaithPath = (SingleFaithPath) (singleplayer.getFaithPath());
        singleFaithPath.moveBlackCross(1);
    }

    private void shuffleDeck(Singleplayer singleplayer){
        singleplayer.shuffleToken();
    }

    /**
     * Move black cross a step in the FaithPath and shuffle the action tokens in the Singleplayer class
     * @param singleplayer the specified player which is playing the current game.
     */
    @Override
    public void triggerEffect(Singleplayer singleplayer) throws NoSuchPlayerException {
        shuffleDeck(singleplayer);
        stepForward(singleplayer);
    }
}
