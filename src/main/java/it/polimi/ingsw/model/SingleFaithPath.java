package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.InvalidStepsException;

public class SingleFaithPath extends FaithPath{
    private int blackCross;
    public SingleFaithPath(Player player){

    }

    /**
     * It moves the blackCross the specified steps
     * @param steps the blackCross has to be moved
     * @throws InvalidStepsException
     */
    public void moveBlackCross(int steps) throws InvalidStepsException {

    }

    /**
     * It moves the blackCross one step.
     * @param player specified player that has not to be moved
     */
    @Override
    public void moveOpponents(Player player) {

    }
}
