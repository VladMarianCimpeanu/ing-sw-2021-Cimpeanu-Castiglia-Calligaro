package it.polimi.ingsw.server.model.stubs;

import it.polimi.ingsw.server.model.DevelopmentCardSet;
import it.polimi.ingsw.server.model.FaithPath;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Identity;
import it.polimi.ingsw.server.model.exceptions.InvalidReadException;
import it.polimi.ingsw.server.model.exceptions.NoSuchPlayerException;
import it.polimi.ingsw.server.model.market.Market;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

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
    public Map<String, Integer> calculatePoints() {
        return null;
    }

    @Override
    public boolean isGameEnded() {
        return false;
    }
    @Override
    public DevelopmentCardSet getDevelopmentCardSet() { return developmentCardSet; }
}
