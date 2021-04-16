package it.polimi.ingsw.model.stubs;

import it.polimi.ingsw.model.DevelopmentCardSet;
import it.polimi.ingsw.model.FaithPath;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Identity;
import it.polimi.ingsw.model.exceptions.InvalidReadException;
import it.polimi.ingsw.model.exceptions.NoSuchPlayerException;
import it.polimi.ingsw.model.market.Market;

import java.io.IOException;
import java.util.ArrayList;

public class GameStub1 extends Game {
    private FaithPathStub1 faithpath;
    private MarketStub1 market;
    private DevelopmentCardSetStub1 developmentCardSet;
    public GameStub1(ArrayList<Identity> idS) throws IOException, InvalidReadException, NoSuchPlayerException {
        super(idS);
        faithpath = new FaithPathStub1();
        market = new MarketStub1();
        developmentCardSet = new DevelopmentCardSetStub1();
    }

    public GameStub1(ArrayList<Identity> idS, DevelopmentCardSetStub1 developmentCardSet) throws IOException, InvalidReadException, NoSuchPlayerException {
        super(idS);
        faithpath = new FaithPathStub1();
        market = new MarketStub1();
        this.developmentCardSet = developmentCardSet;
    }
    @Override
    public Market getMarket(){
        return market;
    }
    @Override
    public FaithPath getFaithPath() {
        return faithpath;
    }
    @Override
    public boolean isGameEnded() {
        return false;
    }
    @Override
    public DevelopmentCardSet getDevelopmentCardSet() { return developmentCardSet; }
}
