package it.polimi.ingsw.model.actionToken;

import it.polimi.ingsw.model.Singleplayer;

public interface ActionToken {
    /**
     * Activate the effect of the ActionToken
     * @param singleplayer use this object to perform the actions of each token
     */
    public void triggerEffect(Singleplayer singleplayer);
}
