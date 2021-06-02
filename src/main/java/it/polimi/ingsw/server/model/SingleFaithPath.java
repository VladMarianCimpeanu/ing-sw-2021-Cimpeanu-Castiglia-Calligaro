package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.exceptions.GameEndedException;
import it.polimi.ingsw.server.model.exceptions.InvalidStepsException;
import it.polimi.ingsw.server.model.exceptions.NoSuchPlayerException;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Represents the faith path whether there is a single player game
 */
public class SingleFaithPath extends FaithPath{
    private int blackCross;
    // each index represent a position of the FaithPath track: a cell contains the amount victory points earned during a popeMeeting
    protected int[] popeMeeting;
    //each stack's element contains the FaithPath's positions that trigger a popeMeeting
    protected Stack<Integer> triggerPopePosition;
    //Map containing Players' positions in the track
    protected Map<Player, Integer> playersPosition;

    public SingleFaithPath(Player player){
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
        triggerPopePosition.add(24);
        triggerPopePosition.add(16);
        triggerPopePosition.add(8);

        // initializing players' position
        playersPosition = new HashMap<>();
        playersPosition.put(player, 0);
        blackCross = 0;
    }

    /**
     * It moves the blackCross the specified steps
     * @param steps the blackCross has to be moved
     */
    public void moveBlackCross(int steps) throws GameEndedException {
        if(steps <= 0) return;
        steps += blackCross;
        for(int i = blackCross+1; i <= steps && i<25; i++) {
            blackCross = i;
            if (!triggerPopePosition.isEmpty() && blackCross == triggerPopePosition.peek()) assignPapalPoints();
        }
        virtualViewUpdate();
        if (blackCross == 24) throw new GameEndedException();
    }

    @Override
    public void movePlayer(Player player, int steps) throws NoSuchPlayerException, InvalidStepsException, GameEndedException {
        if(steps < 0) throw new InvalidStepsException("Negative numbers of steps are not allowed. Number found is: "+steps);
        if(player == null) throw new NoSuchPlayerException();
        if(!playersPosition.containsKey(player)) throw new NoSuchPlayerException();
        int pre_steps = playersPosition.get(player);
        for(int i = pre_steps+1; i<=pre_steps+steps && i < 25; i++) {
            playersPosition.put(player, i);
            if (i == triggerPopePosition.peek())
                assignPapalPoints();
        }
        virtualViewUpdate();
        if(playersPosition.get(player) == 24) throw new GameEndedException();
    }

    /**
     * It moves the blackCross one step.
     * @param player specified player that has not to be moved
     */
    @Override
    public void moveOpponents(Player player) throws NoSuchPlayerException, GameEndedException {
        if(player == null) throw new NoSuchPlayerException();
        if(!playersPosition.containsKey(player)) throw new NoSuchPlayerException();
        moveBlackCross(1);
    }

    @Override
    public void assignVictoryPoints(Player player) throws NoSuchPlayerException {
        if(player == null) throw new NoSuchPlayerException();
        if(!playersPosition.containsKey(player)) throw new NoSuchPlayerException();
        player.addVictoryPoints(getVictoryPoints(playersPosition.get(player)));
    }

    @Override
    public int getPlayerPosition(Player player) throws NoSuchPlayerException{
        if(player == null) throw new NoSuchPlayerException();
        if(!playersPosition.containsKey(player)) throw new NoSuchPlayerException();
        return playersPosition.get(player);
    }

    public int getBlackCrossPosition() {
        return blackCross;
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

    private void virtualViewUpdate() {
        Map<String, Integer> updatedPositions= new HashMap<>();
        Player actualPlayer = (Player)playersPosition.keySet().toArray()[0];
        updatedPositions.put(actualPlayer.getNickName(), playersPosition.get(actualPlayer));
        updatedPositions.put("blackCross", blackCross);
        getVirtualView().updateSingleFaithPath(updatedPositions);
    }
}
