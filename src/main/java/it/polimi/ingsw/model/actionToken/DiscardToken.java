package it.polimi.ingsw.model.actionToken;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.DevelopmentCardSet;
import it.polimi.ingsw.model.Singleplayer;
import it.polimi.ingsw.model.exceptions.NoCardException;
import it.polimi.ingsw.model.exceptions.WrongLevelException;

public class DiscardToken implements ActionToken {
    private final Color color;

    public DiscardToken(Color color){
        this.color = color;
    }

    /**
     * Discard two cards of the same color of the ActionToken, starting from level one
     * @param singleplayer used to get access to the Development Card Set
     */
    @Override
    public void triggerEffect(Singleplayer singleplayer) {
        DevelopmentCardSet tempDeck = singleplayer.getDevelopmentCardSet();
        int cardsToDraw = 2;
        boolean noMoreCards = false;
        int level = 1;
        while (cardsToDraw > 0 && !noMoreCards) {
            try {
                tempDeck.drawCard(color, level);
                cardsToDraw -= 1;
            } catch (WrongLevelException e) {
                noMoreCards = true;
            } catch (NoCardException e) {
                level += 1;
            }
        }
    }
}
