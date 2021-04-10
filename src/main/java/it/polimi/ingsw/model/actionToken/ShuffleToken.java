package it.polimi.ingsw.model.actionToken;

import it.polimi.ingsw.model.SingleFaithPath;
import it.polimi.ingsw.model.Singleplayer;
import it.polimi.ingsw.model.exceptions.NoSuchPlayerException;

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
     * Move black cross of a step in the FaithPath and shuffle the tokens in the Singleplayer class
     * @param singleplayer
     */
    @Override
    public void triggerEffect(Singleplayer singleplayer) throws NoSuchPlayerException {
        shuffleDeck(singleplayer);
        stepForward(singleplayer);
    }
}
