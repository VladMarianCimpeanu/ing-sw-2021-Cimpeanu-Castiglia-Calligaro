package it.polimi.ingsw.model.actionToken;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Singleplayer;

public class DiscardToken implements ActionToken {
    private Color color;

    public DiscardToken(Color color){
        this.color = color;
    }

    /**
     * Discard two cards of the same color of the ActionToken, starting from level one
     * @param singleplayer used to get access to the Development Card Set
     */
    @Override
    public void triggerEffect(Singleplayer singleplayer) {

    }
}
