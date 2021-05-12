package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.InvalidReadException;
import it.polimi.ingsw.model.exceptions.InvalidStepsException;
import it.polimi.ingsw.model.exceptions.NoSuchPlayerException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * This class is used to represent the multiplayer game mode.
 */
public class Multiplayer extends Game {
    public Multiplayer(ArrayList<Identity> identities) throws InvalidStepsException, NoSuchPlayerException, IOException, InvalidReadException {
        super(identities);
        if(getPlayers() == null) return;
        faithPath = new MultiFaithPath(getPlayers());
    }

    @Override
    public boolean isGameEnded() {
        return false;
    }
}
