package it.polimi.ingsw.client.panels;

import it.polimi.ingsw.client.Clickable;

import java.lang.reflect.Array;
import java.util.ArrayList;

public interface MovePanel {

    /**
     * checks if the gameBoard was clickable before the activation of the current panel.
     * @return true if the gameBoard was clickable, else false.
     */
    boolean wasBoardUnlocked();

    /**
     * gets the action panel that was used before this panel was created.
     * @return the last action panel used.
     */
    ActionPanel getLastPanel();

    /**
     * all the objects that were clickable during the last dialog are clickable again.
     */
    void restoreClickable();

    /**
     * @return all clickable objects related to the action panel used before this panel was created
     */
    ArrayList<Clickable> getLastClickable();
}
