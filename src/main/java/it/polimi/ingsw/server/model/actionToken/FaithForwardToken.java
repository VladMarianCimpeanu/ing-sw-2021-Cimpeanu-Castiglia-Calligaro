package it.polimi.ingsw.server.model.actionToken;

import it.polimi.ingsw.server.model.SingleFaithPath;
import it.polimi.ingsw.server.model.Singleplayer;


/**
 * This class represents a specific type of action token.
 */
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

    @Override
    public int getID() {
        return 5;
    }
}
