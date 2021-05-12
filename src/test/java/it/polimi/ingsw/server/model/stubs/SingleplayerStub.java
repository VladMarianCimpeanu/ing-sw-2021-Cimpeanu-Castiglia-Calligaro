package it.polimi.ingsw.server.model.stubs;

import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.model.exceptions.InvalidReadException;
import it.polimi.ingsw.server.model.exceptions.NoSuchPlayerException;

import java.io.IOException;
import java.util.ArrayList;

public class SingleplayerStub extends Singleplayer {
    private DevelopmentCardSet developmentCardSet;
    private FaithPath faithPath;
    public boolean shuffled;

    public SingleplayerStub(DevelopmentCardSet developmentCardSet, FaithPath faithPath, ArrayList<Identity> identities) throws IOException, InvalidReadException, NoSuchPlayerException {
        super(identities);
        this.developmentCardSet = developmentCardSet;
        this.faithPath = faithPath;
        shuffled = false;
    }

    @Override
    public DevelopmentCardSet getDevelopmentCardSet() {
        return developmentCardSet;
    }

    @Override
    public FaithPath getFaithPath() {
        return faithPath;
    }

    @Override
    public void shuffleToken() {
        this.shuffled = true;
    }
}
