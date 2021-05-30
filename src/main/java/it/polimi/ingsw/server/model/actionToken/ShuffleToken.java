package it.polimi.ingsw.server.model.actionToken;

import it.polimi.ingsw.server.model.SingleFaithPath;
import it.polimi.ingsw.server.model.Singleplayer;
import it.polimi.ingsw.server.model.exceptions.GameEndedException;
import it.polimi.ingsw.server.model.exceptions.NoSuchPlayerException;

/**
 * This class represents a specific type of action token.
 */
public class ShuffleToken implements ActionToken{

    private void stepForward(Singleplayer singleplayer) throws NoSuchPlayerException, GameEndedException {
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
    public void triggerEffect(Singleplayer singleplayer) throws NoSuchPlayerException, GameEndedException {
        shuffleDeck(singleplayer);
        stepForward(singleplayer);
    }

    @Override
    public int getID() {
        return 4;
    }
}
