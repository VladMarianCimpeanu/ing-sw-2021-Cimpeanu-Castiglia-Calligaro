package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.InvalidStepsException;
import it.polimi.ingsw.model.exceptions.NoSuchPlayerException;

import java.util.ArrayList;

public class Multiplayer extends Game {
    public Multiplayer(ArrayList<Identity> identities) throws InvalidStepsException, NoSuchPlayerException {
        super(identities);
        faithPath = new MultiFaithPath(getPlayers());
        for (int i = 0; i < 4; i++)
            faithPath.movePlayer(getPlayers().get(i), i / 2);
        //how to handle the bonus resources?
    }
    /*
    public Multiplayer(ArrayList<Identity> identities){
        super(identities);
    }
    */

    @Override
    public boolean isGameEnded() {
        return false;
    }
}
