package it.polimi.ingsw.model.actionToken;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.DevelopmentCardSet;
import it.polimi.ingsw.model.Singleplayer;
import it.polimi.ingsw.model.exceptions.NoCardException;
import it.polimi.ingsw.model.exceptions.WrongLevelException;

/**
 * This class represents a specific type of Action token.
 */
public class DiscardToken implements ActionToken {
    private final Color color;

    public DiscardToken(Color color){
        this.color = color;
    }

    /**
     *
     * @return the color of development cards which this token can discard.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Discard two development cards on the top of the deck which contains development cards of the same color as this token indicates.
     * The method starts looking for tokens from level 1, and if they do not exist, it searches higher level development cards.
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
