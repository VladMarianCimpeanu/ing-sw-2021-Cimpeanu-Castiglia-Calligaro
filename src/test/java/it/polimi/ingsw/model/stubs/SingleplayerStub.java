package it.polimi.ingsw.model.stubs;

import it.polimi.ingsw.model.DevelopmentCardSet;
import it.polimi.ingsw.model.FaithPath;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Singleplayer;

import java.util.ArrayList;

public class SingleplayerStub extends Singleplayer {
    private DevelopmentCardSet developmentCardSet;
    private FaithPath faithPath;
    public boolean shuffled;

    public SingleplayerStub(DevelopmentCardSet developmentCardSet, FaithPath faithPath) {
        super(null);
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
