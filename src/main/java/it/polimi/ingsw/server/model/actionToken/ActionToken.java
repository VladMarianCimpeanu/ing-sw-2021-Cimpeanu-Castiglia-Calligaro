package it.polimi.ingsw.server.model.actionToken;

import it.polimi.ingsw.server.model.Singleplayer;
import it.polimi.ingsw.server.model.exceptions.NoSuchPlayerException;

/**
 * This class represent a general Action token.
 */
public interface ActionToken {
    /**
     * Activate the effect of the ActionToken
     * @param singleplayer use this object to perform the actions of each token
     */
    void triggerEffect(Singleplayer singleplayer) throws NoSuchPlayerException;
    int getID();
}
