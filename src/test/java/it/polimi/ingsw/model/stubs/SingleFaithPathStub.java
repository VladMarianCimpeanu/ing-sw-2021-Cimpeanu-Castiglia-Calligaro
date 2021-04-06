package it.polimi.ingsw.model.stubs;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.SingleFaithPath;
import it.polimi.ingsw.model.exceptions.InvalidStepsException;



public class SingleFaithPathStub extends SingleFaithPath {
    public int cross;

    public SingleFaithPathStub() {
        super(null);
        cross = 0;
    }

    @Override
    public void moveBlackCross(int steps) throws InvalidStepsException {
        this.cross += steps;
    }
}
