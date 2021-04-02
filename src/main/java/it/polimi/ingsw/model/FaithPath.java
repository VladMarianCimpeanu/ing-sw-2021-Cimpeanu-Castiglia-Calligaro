package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.InvalidStepsException;
import it.polimi.ingsw.model.exceptions.NoSuchPlayerException;

import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;

public abstract class FaithPath {

    // each index correspond to a cell of the FaithPath track: each victoryPoints' element is the amount of victory points that the cell offers at the end of the game
    private static int[] victoryPoints;
    // each index represent a position of the FaithPath track: a cell contains the amount victory points earned during a popeMeeting
    private int[] popeMeeting;
    //each stack's element contains the FaithPath's positions that trigger a popeMeeting
    private Stack<Integer> triggerPopePosition;
    //Map containing Players' positions in the track
    private Map<Player, Integer> playersPosition;


    /**
     * it moves the specified player the specified steps on the track and checks if a popeMeeting is triggered
     * @param player specified player that has to be moved
     * @param steps specified number of steps
     * @throws NoSuchPlayerException
     * @throws InvalidStepsException
     */
    public void movePlayer(Player player, int steps) throws NoSuchPlayerException, InvalidStepsException {
         //TO DO
    }

    /**
     *it moves all the players one step a part from the specified player and checks if a popeMeeting is triggered.
     * @param player specified player that has not to be moved
     * @throws NoSuchPlayerException
     */
    public abstract void moveOpponents(Player player) throws NoSuchPlayerException;

    /**
     * it adds the amount of victory points earned to the specific player at the end of the game
     * @param player
     * @throws NoSuchPlayerException
     */
    public void assignVictoryPoints(Player player) throws NoSuchPlayerException{

    }

    /**
     * this method increases victory points of each player placed in a pope position meeting
     */
    private void assignPapalPoints(){

    }
}
