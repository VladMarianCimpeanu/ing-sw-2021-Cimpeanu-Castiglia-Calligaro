package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Stack;
import it.polimi.ingsw.model.exceptions.*;

public class DevelopmentCardSet {
    private ArrayList<ArrayList<Stack<DevelopmentCard>>> availableDevelopmentCards;

    /**
     * Select a deck by color and level, and check what's the card on top
     * @param color
     * @param level
     * @throws WrongLevelException
     * @return
     */
    public DevelopmentCard peekCard(Color color, int level) throws WrongLevelException {
        return null;
    }

    /**
     * Select a deck by color and level, and pick the card on top
     * @param color
     * @param level
     * @throws WrongLevelException
     * @return
     */
    public DevelopmentCard drawCard(Color color, int level) throws WrongLevelException {
        return null;
    }
}
