package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.InvalidStepsException;
import it.polimi.ingsw.model.exceptions.NoSuchPlayerException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class MultiFaithPath extends FaithPath{
    // each index represent a position of the FaithPath track: a cell contains the amount victory points earned during a popeMeeting
    private int[] popeMeeting;
    //each stack's element contains the FaithPath's positions that trigger a popeMeeting
    private Stack<Integer> triggerPopePosition;
    //Map containing Players' positions in the track
    private Map<Player, Integer> playersPosition;

    public MultiFaithPath(ArrayList<Player> players){
        super();
        // initializing popeMeeting cells:
        int index = 0;
        popeMeeting = new int[25];
        for(; index < 5; index ++) popeMeeting[index] = 0;
        for(index = 9; index < 12; index ++ ) popeMeeting[index] = 0;
        popeMeeting[17] = 0;
        popeMeeting[18] = 0;
        for(index = 5; index < 9; index ++ ) popeMeeting[index] = 2;
        for(index = 12; index < 17 ; index ++ ) popeMeeting[index] = 3;
        for(index = 19; index < 24; index ++ ) popeMeeting[index] = 4;

        // initializing triggerPopePosition
        triggerPopePosition = new Stack<Integer>();
        triggerPopePosition.add(24);
        triggerPopePosition.add(16);
        triggerPopePosition.add(9);

        // initializing players' position
        playersPosition = new HashMap<>();
        for(Player player : players) playersPosition.put(player, 0);
    }

    @Override
    public void movePlayer(Player player, int steps) throws NoSuchPlayerException, InvalidStepsException {

    }

    @Override
    public void moveOpponents(Player player) throws NoSuchPlayerException {

    }

    @Override
    public void assignVictoryPoints(Player player) throws NoSuchPlayerException {

    }

    @Override
    public int getPlayerPosition(Player player) throws NoSuchPlayerException {
        return 0;
    }

    @Override
    public Map<Player, Integer> getAllPositions() {
        return null;
    }

    /**
     * this method increases victory points of each player placed in a pope position meeting
     */
    private void assignPapalPoints(){

    }
}
