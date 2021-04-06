package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.InvalidStepsException;
import it.polimi.ingsw.model.exceptions.NoSuchPlayerException;

import java.util.Map;


public abstract class FaithPath {

    // each index correspond to a cell of the FaithPath track: each victoryPoints' element is the amount of victory points that the cell offers at the end of the game
    private static int[] victoryPoints;

    public FaithPath() {

        // initializing victory points:     MAYBE IN THE FUTURE SINGLETON????
        victoryPoints = new int[25];
        int cell = 0;
        for (; cell < 3; cell ++ ) victoryPoints[cell] = 0;
        for (; cell < 6; cell ++ ) victoryPoints[cell] = 1;
        for (; cell < 9; cell ++ ) victoryPoints[cell] = 2;
        for (; cell < 12; cell ++ ) victoryPoints[cell] = 4;
        for (; cell < 15; cell ++ ) victoryPoints[cell] = 6;
        for (; cell < 18; cell ++ ) victoryPoints[cell] = 9;
        for (; cell < 21; cell ++ ) victoryPoints[cell] = 12;
        for (; cell < 24; cell ++ ) victoryPoints[cell] = 16;
        victoryPoints[cell] = 20;

    }

    /**
     * it moves the specified player the specified steps on the track and checks if a popeMeeting is triggered
     * @param player specified player that has to be moved
     * @param steps specified number of steps
     * @throws NoSuchPlayerException
     * @throws InvalidStepsException
     */
    public abstract void movePlayer(Player player, int steps) throws NoSuchPlayerException, InvalidStepsException;

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
    public abstract void assignVictoryPoints(Player player) throws NoSuchPlayerException;


    /**
     * given a certain position, it returns the amount of points earned if the player is in that position at the end of the game.
     * @param position specified position of the faithPath
     * @return amount of points that a player should earn in that position.
     */
    protected int getVictoryPoints(int position) {
        return 0;
    }

    public abstract int getPlayerPosition(Player player) throws NoSuchPlayerException;
    public abstract Map<Player, Integer> getAllPositions();
}
