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

    private boolean someone_end;

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
        for(index = 19; index < 25; index ++ ) popeMeeting[index] = 4;

        // initializing triggerPopePosition
        triggerPopePosition = new Stack<Integer>();
        triggerPopePosition.push(24);
        triggerPopePosition.push(16);
        triggerPopePosition.push(8);

        // initializing players' position
        playersPosition = new HashMap<>();
        for(Player player : players) playersPosition.put(player, 0);

        someone_end = false;
    }

    @Override
    public void movePlayer(Player player, int steps) throws NoSuchPlayerException, InvalidStepsException {
        if(steps < 0) throw new InvalidStepsException("Negative numbers of steps are not allowed. Number found is: "+steps);
        if(player == null) throw new NoSuchPlayerException();
        if(!playersPosition.containsKey(player)) throw new NoSuchPlayerException();
        int pre_steps = playersPosition.get(player);
        for(int i = pre_steps+1; i<=pre_steps+steps && i < 25; i++) {
            playersPosition.put(player, i);
            if (i == triggerPopePosition.peek())
                assignPapalPoints();
            if(i == 24) someone_end = true;
        }
    }

    @Override
    public void moveOpponents(Player player) throws NoSuchPlayerException, InvalidStepsException {
        if(player == null) throw new NoSuchPlayerException();
        if(!playersPosition.containsKey(player)) throw new NoSuchPlayerException();
        for (Player p: playersPosition.keySet()) {
            if(p == player) continue;
            int pre_steps  = playersPosition.get(p);
            playersPosition.put(p, pre_steps+1);
            if(playersPosition.get(p) == 24) someone_end = true;
        }
        for (Player p: playersPosition.keySet())
            if(playersPosition.get(p) == triggerPopePosition.peek()) assignPapalPoints();
    }

    @Override
    public void assignVictoryPoints(Player player) throws NoSuchPlayerException {
        if(player == null) return;
        if(!playersPosition.containsKey(player)) throw new NoSuchPlayerException();
        player.addVictoryPoints(getVictoryPoints(playersPosition.get(player)));
    }

    @Override
    public int getPlayerPosition(Player player) throws NoSuchPlayerException {
        if(player == null) throw new NoSuchPlayerException();
        if(!playersPosition.containsKey(player)) throw new NoSuchPlayerException();
        return playersPosition.get(player);
    }

    @Override
    public Map<Player, Integer> getAllPositions() {
        return playersPosition;
    }

    /**
     * this method increases victory points of each player placed in a pope position meeting
     */
    private void assignPapalPoints(){
        for (Player p : playersPosition.keySet())
            p.addVictoryPoints(popeMeeting[playersPosition.get(p)]);
        switch(triggerPopePosition.peek()){
            case 8:
                for(int index = 0; index < 9; index ++ ) popeMeeting[index] = 0;
                break;
            case 16:
                for(int index = 0; index < 17 ; index ++ ) popeMeeting[index] = 0;
                break;
            case 24:
                for(int index = 0; index < 25; index ++ ) popeMeeting[index] = 0;
                break;
            default:
                break;
        }
        triggerPopePosition.pop();
    }

}
