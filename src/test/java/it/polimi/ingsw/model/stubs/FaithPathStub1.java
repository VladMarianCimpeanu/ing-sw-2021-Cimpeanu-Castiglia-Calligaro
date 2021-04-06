package it.polimi.ingsw.model.stubs;

import it.polimi.ingsw.model.FaithPath;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.exceptions.NoSuchPlayerException;

import java.util.Map;

public class FaithPathStub1 extends FaithPath {
    private boolean haveMovedOpponent;
    private int stepsPlayer;
    private Player playerPlaying;
    public FaithPathStub1(){
        //super?
        haveMovedOpponent = false;
        stepsPlayer = 0;
        playerPlaying = null;
    }
    @Override
    public void moveOpponents(Player player){
        haveMovedOpponent = true;
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

    @Override
    public void movePlayer(Player player, int steps){
        stepsPlayer = steps;
        playerPlaying = player;
    }

    public Player getPlayerPlaying() {
        return playerPlaying;
    }

    public int getStepsPlayer() {
        return stepsPlayer;
    }

    public boolean isHaveMovedOpponent() {
        return haveMovedOpponent;
    }
}
