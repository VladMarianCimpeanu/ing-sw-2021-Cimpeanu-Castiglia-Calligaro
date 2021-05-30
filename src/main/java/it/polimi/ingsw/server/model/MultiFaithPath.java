package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.exceptions.GameEndedException;
import it.polimi.ingsw.server.model.exceptions.InvalidStepsException;
import it.polimi.ingsw.server.model.exceptions.NoSuchPlayerException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Represents the faith path whether there is a multiplayer game
 */
public class MultiFaithPath extends FaithPath{
    // each index represent a position of the FaithPath track: a cell contains the amount victory points earned during a popeMeeting
    private int[] popeMeeting;
    //each stack's element contains the FaithPath's positions that trigger a popeMeeting
    private Stack<Integer> triggerPopePosition;
    //Map containing Players' positions in the track
    private Map<Player, Integer> playersPosition;

    private boolean someoneEnd;

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
        triggerPopePosition = new Stack<>();
        triggerPopePosition.push(24);
        triggerPopePosition.push(16);
        triggerPopePosition.push(8);

        // initializing players' position
        playersPosition = new HashMap<>();
        for(Player player : players) playersPosition.put(player, 0);

        someoneEnd = false;
    }

    @Override
    public void movePlayer(Player player, int steps) throws NoSuchPlayerException, InvalidStepsException, GameEndedException {
        if(steps < 0) throw new InvalidStepsException("Negative numbers of steps are not allowed. Number found is: "+steps);
        if(player == null) throw new NoSuchPlayerException();
        if(!playersPosition.containsKey(player)) throw new NoSuchPlayerException();
        int pre_steps = playersPosition.get(player);
        for(int i = pre_steps+1; i<=pre_steps+steps && i < 25; i++) {
            playersPosition.put(player, i);
            if (i == triggerPopePosition.peek()) {
                //getVirtualView().updateFaithPath(playersPosition);
                assignPapalPoints();
            }
            if(i == 24) {
                someoneEnd = true;
                throw new GameEndedException();
            }
        }
        getVirtualView().updateFaithPath(playersPosition);
    }

    @Override
    public void moveOpponents(Player player) throws NoSuchPlayerException, InvalidStepsException, GameEndedException {
        if(player == null) throw new NoSuchPlayerException();
        if(!playersPosition.containsKey(player)) throw new NoSuchPlayerException();
        for (Player p: playersPosition.keySet()) {
            if(p == player) continue;
            int pre_steps  = playersPosition.get(p);
            playersPosition.put(p, pre_steps+1);
            if(playersPosition.get(p) == 24) {
                someoneEnd = true;
            }
        }
        getVirtualView().updateFaithPath(playersPosition);
        for (Player p: playersPosition.keySet()) {
            if (playersPosition.get(p) == triggerPopePosition.peek()) assignPapalPoints();
        }
        if(someoneEnd) throw new GameEndedException();
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
        Map<Player, Integer> deltaVictoryPoints = new HashMap<>();
        for (Player p : playersPosition.keySet()) {
            p.addVictoryPoints(popeMeeting[playersPosition.get(p)]);
            deltaVictoryPoints.put(p, popeMeeting[playersPosition.get(p)]);
        }
        getVirtualView().updateMeetingPope(deltaVictoryPoints);
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
