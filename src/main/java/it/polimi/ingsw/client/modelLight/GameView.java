package it.polimi.ingsw.client.modelLight;

import java.util.ArrayList;
import java.util.Map;

public abstract class GameView {
    protected Map<String, PlayerView> players;
    protected MarketView market;
    protected FaithPathView faithPath;
    protected DevelopmentCardSetView cards;

    public FaithPathView getFaithPathView(){
        return faithPath;
    }
}
