package it.polimi.ingsw.server.model.stubs;

import it.polimi.ingsw.server.model.SingleFaithPath;


public class SingleFaithPathStub extends SingleFaithPath {
    public int cross;

    public SingleFaithPathStub() {
        super(null);
        cross = 0;
    }

    @Override
    public void moveBlackCross(int steps)  {
        this.cross += steps;
    }
}
