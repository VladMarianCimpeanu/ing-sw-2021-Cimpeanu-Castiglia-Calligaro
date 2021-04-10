package it.polimi.ingsw.model.actionToken;

import it.polimi.ingsw.model.SingleFaithPath;
import it.polimi.ingsw.model.Singleplayer;

public class FaithForwardToken implements ActionToken{

    /**
     * Move BlackCross of 2 steps in the FaithPath
     * @param singleplayer specified player to apply this effect
     */
    @Override
    public void triggerEffect(Singleplayer singleplayer) {
        SingleFaithPath singleFaithPath = (SingleFaithPath) singleplayer.getFaithPath();
        singleFaithPath.moveBlackCross(2);
    }
}
