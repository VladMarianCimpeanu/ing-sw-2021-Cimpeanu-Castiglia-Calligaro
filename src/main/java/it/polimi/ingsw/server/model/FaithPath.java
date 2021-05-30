package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.controller.VirtualView;
import it.polimi.ingsw.server.model.exceptions.GameEndedException;
import it.polimi.ingsw.server.model.exceptions.InvalidStepsException;
import it.polimi.ingsw.server.model.exceptions.NoSuchPlayerException;

import java.util.Map;

/**
 * Represents the faith path related to a game
 * A game has the reference to only one faith path (different from other games), so this is unique for each player playing in the game
 */

public abstract class FaithPath {
    // each index correspond to a cell of the FaithPath track: each victoryPoints' element is the amount of victory points that the cell offers at the end of the game
    private static int[] victoryPoints;
    private VirtualView virtualView;

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
    public abstract void movePlayer(Player player, int steps) throws NoSuchPlayerException, InvalidStepsException, GameEndedException;

    /**
     *it moves all the players one step a part from the specified player and checks if a popeMeeting is triggered.
     * @param player specified player that has not to be moved
     * @throws NoSuchPlayerException
     */
    public abstract void moveOpponents(Player player) throws NoSuchPlayerException, InvalidStepsException, GameEndedException;

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
        return victoryPoints[position];
    }

    public abstract int getPlayerPosition(Player player) throws NoSuchPlayerException;
    public abstract Map<Player, Integer> getAllPositions();
    public void subscribe(VirtualView virtualView){
        this.virtualView = virtualView;
    }

    public VirtualView getVirtualView() {
        return virtualView;
    }
}
